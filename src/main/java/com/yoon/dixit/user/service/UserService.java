package com.yoon.dixit.user.service;

import com.yoon.dixit.user.enums.PlayingStatus;
import com.yoon.dixit.user.vo.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UsersService usersService;

    public User login(String id) {
        User user = usersService.get(id);

        if (Objects.isNull(user)) {
            user = User.builder()
                    .id(id)
                    .playingStatus(PlayingStatus.WAITING)
                    .build();
            usersService.add(user);
        }

        return usersService.get(user.getId());
    }

    public void logout(String id) {
        usersService.remove(id);
    }

    public void finishGame(String id) {
        usersService.get(id).setPlayingStatus(PlayingStatus.WAITING);
    }

    public void ready(String id) {
        usersService.get(id).setPlayingStatus(PlayingStatus.READY);
    }

    public void unready(String id) {
        usersService.get(id).setPlayingStatus(PlayingStatus.WAITING);
    }

    public void play(String id) {
        usersService.get(id).setPlayingStatus(PlayingStatus.PLAYING);
    }

    public boolean isLeader(String id) {
        return usersService.isLeader(id);
    }
}
