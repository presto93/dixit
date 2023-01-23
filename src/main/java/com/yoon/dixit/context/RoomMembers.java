package com.yoon.dixit.context;

import com.yoon.dixit.enums.Step;
import com.yoon.dixit.room.vo.RoomMember;
import com.yoon.dixit.user.vo.User;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RoomMembers {

    private final List<RoomMember> roomMembers = new ArrayList<>();

    public void add(RoomMember room) {
        roomMembers.add(room);
    }

    public void remove(User user) {
        remove(getOrThrow(user));
    }

    public void remove(RoomMember room) {
        roomMembers.remove(room);
    }

    public RoomMember getOrThrow(User user) {
        return get(user).orElseThrow(() ->
                new IllegalArgumentException(String.format("Invalid user id '%s'.", user.getId()))
        );
    }

    public Optional<RoomMember> get(User user) {
        return roomMembers.stream()
                .filter(roomMember -> user == roomMember.getUser())
                .findFirst();
    }


    public Step getStep() {
        if (CollectionUtils.isEmpty(roomMembers)) {
            return Step.WAITING;
        }

        return roomMembers.stream().reduce((m1, m2) -> {
            if (m1.getStep().getOrder() < m2.getStep().getOrder()) {
                return m1;
            }
            return m2;
        }).orElseThrow().getStep();
    }

    public boolean allInSameStep() {
        if (CollectionUtils.size(roomMembers) <= 1) {
            return true;
        }

        Step roomStep = roomMembers.get(0).getStep();

        return roomMembers.stream().anyMatch(roomMember -> roomMember.getStep() != roomStep);
    }
}
