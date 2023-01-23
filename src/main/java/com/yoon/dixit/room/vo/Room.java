package com.yoon.dixit.room.vo;

import com.yoon.dixit.context.RoomMembers;
import com.yoon.dixit.enums.Step;
import com.yoon.dixit.play.vo.Card;
import lombok.*;

import java.util.LinkedList;
import java.util.List;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Setter
@Getter
@ToString
public class Room {

    private int id;

    @Builder.Default
    private RoomMembers roomMembers = new RoomMembers();

    private LinkedList<Card> dummy;

    @Builder.Default
    private Card targetCard = null;

    @Builder.Default
    private RoomMember leader = null;

    @Builder.Default
    @Setter(AccessLevel.NONE)
    private int minUserNumber = 4;

    @Builder.Default
    @Setter(AccessLevel.NONE)
    private int maxUserNumber = 8;

    @Builder.Default
    @Setter(AccessLevel.NONE)
    private boolean infiniteMode = false;

    @Builder.Default
    @Setter(AccessLevel.NONE)
    private int maxNumberOfCard = 6;

    public Step getStep() {
        return roomMembers.getStep();
    }

    public boolean allInSameStep() {
        return roomMembers.allInSameStep();
    }
}
