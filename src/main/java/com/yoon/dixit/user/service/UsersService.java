package com.yoon.dixit.user.service;

import com.yoon.dixit.user.vo.User;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.*;

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

        int firstIndex = RandomUtils.nextInt(0, MapUtils.size(users));

        Iterator<User> userIterable = users.values().iterator();

        for (int i = 0; i < firstIndex; i++) {
            leader = userIterable.next();
        }
    }

    synchronized public int getUserCount() {
        return MapUtils.size(users);
    }

    public void clearUsers() {
        leader = null;
        users.clear();
    }

    public void add(User user) {
        users.put(user.getId(), user);
        if (Objects.isNull(leader)) {
            leader = user;
        }
    }

    public void remove(String id) {
        remove(users.get(id));
    }

    public void remove(User user) {
        users.remove(user.getId());

        if (isLeader(user.getId())) {
            changeLeader();
        }

    }

    public boolean isLeader(String id) {
        return StringUtils.equals(id, leader.getId());
    }
}
