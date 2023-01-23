package com.yoon.dixit.room.vo;

import com.yoon.dixit.enums.Step;
import com.yoon.dixit.play.vo.Card;
import com.yoon.dixit.user.vo.User;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Setter
@Getter
@ToString
public class RoomMember {

    private User user;

    @Builder.Default
    private List<Card> cards = new ArrayList<>();

    @Builder.Default
    private Card selectedCard = null;

    @Builder.Default
    private int score = 0;

    @Builder.Default
    private Step step = Step.WAITING;

    public void toNextStep() {
        step = step.next();
    }
}
