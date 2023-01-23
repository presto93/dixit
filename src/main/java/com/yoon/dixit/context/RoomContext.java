package com.yoon.dixit.context;

import com.yoon.dixit.enums.Step;
import com.yoon.dixit.exceptions.IllegalPlayingStatusException;
import com.yoon.dixit.play.vo.Card;
import com.yoon.dixit.room.vo.Room;
import com.yoon.dixit.room.vo.RoomMember;
import com.yoon.dixit.user.enums.PlayingStatus;
import com.yoon.dixit.user.vo.User;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@RequiredArgsConstructor
public class RoomContext {

    private static int lastRoomId = 0;
    private final Rooms rooms = new Rooms();

    private final CardService cardService;

    // create
    public Room createRoom() {

        Room room = Room.builder()
                .id(lastRoomId++)
                .dummy(cardService.loadCards())
                .build();

        rooms.add(room);

        return room;
    }

    // update
    public Room join(int roomId, User user) {

        Room room = rooms.getOrThrow(roomId);

        if (room.getStep() != Step.WAITING && room.getStep() != Step.READY) {
            throw new IllegalPlayingStatusException(Step.READY, room.getStep());
        }

        RoomMember roomMember = RoomMember.builder()
                .user(user)
                .build();

        if (room.getLeader() == null) {
            room.setLeader(roomMember);
        }

        room.getRoomMembers().add(roomMember);

        return room;
    }

    public List<Card> getCards(int roodId, User user) {

        Room room = rooms.getOrThrow(roodId);

        RoomMember roomMember = room.getRoomMembers().getOrThrow(user);


        LinkedList<Card> dummy = room.getDummy();

        List<Card> userCards = roomMember.getCards();

        for (int numberOfCards = userCards.size(); numberOfCards < room.getMaxNumberOfCard(); numberOfCards++) {
            userCards.add(dummy.pollFirst());
        }

    }



    public void leave(int roomId, User user) {

        Room room = rooms.getOrThrow(roomId);

        RoomMember roomMember = room.getRoomMembers().getOrThrow(user);

        room.getRoomMembers().remove(roomMember);
    }
}
