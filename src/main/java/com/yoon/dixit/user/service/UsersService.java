package com.yoon.dixit.user.service;

import com.yoon.dixit.user.dto.UserDto;
import com.yoon.dixit.user.enums.PlayingStatus;
import com.yoon.dixit.user.enums.ReadyStatus;
import com.yoon.dixit.user.vo.User;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UsersService {

    private static final Map<String, User> users = new HashMap<>();
    private static User leader;

    public User get(String id) {
        return users.get(id);
    }

    public void changeLeader() {
        if (MapUtils.isEmpty(users)) {
            return;
        }

        leader.setLeader(false);

        int firstIndex = RandomUtils.nextInt(0, MapUtils.size(users));

        Iterator<User> userIterable = users.values().iterator();

        for (int i = 0; i < firstIndex; i++) {
            setLeader(userIterable.next());
        }
        leader.setLeader(true);
    }

    public void changeLeader(String userId) {
        if (MapUtils.isEmpty(users)) {
            return;
        }

        setLeader(users.get(userId));
    }

    synchronized public int getUserCount() {
        return MapUtils.size(users);
    }

    public void clearUsers() {
        setLeader(null);
        users.clear();
    }

    public void add(User user) {
        users.put(user.getId(), user);
        if (Objects.isNull(leader)) {
            setLeader(user);
        }
    }

    public void remove(String id) {
        User user = users.get(id);

        remove(user);
    }

    public void remove(User user) {
        if (isLeader(user.getId())) {
            changeLeader();
        }
        users.remove(user.getId());
    }

    public boolean isLeader(String id) {
        return StringUtils.equals(id, leader.getId());
    }

    public ReadyStatus getReadyStatus(int minPayerCount) {

        if (MapUtils.size(users) < minPayerCount) {
            return ReadyStatus.WAITING;
        }

        Map<PlayingStatus, List<User>> groupedUsers = users.values().stream().collect(Collectors.groupingBy(User::getPlayingStatus));

        int readyCount = CollectionUtils.size(groupedUsers.get(PlayingStatus.READY));

        if (readyCount == 0) {
            return ReadyStatus.NONE;
        }
        if (readyCount == MapUtils.size(users)) {
            return ReadyStatus.ALL;
        }

        return ReadyStatus.SOME;
    }

    private void setLeader(User newLeader) {
        if (Objects.nonNull(leader)) {
            leader.setLeader(false);
        }

        leader = newLeader;

        if (Objects.nonNull(leader)) {
            leader.setLeader(true);
        }
    }

    public List<UserDto> getAll() {
        return users.values()
                .stream()
                .map(User::toDto)
                .collect(Collectors.toList());
    }

    public void startGame() {
        users.forEach((userId, user) ->
                user.setPlayingStatus(PlayingStatus.CHECK_CARD)
        );
    }
}
