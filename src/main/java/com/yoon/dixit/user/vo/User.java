package com.yoon.dixit.user.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yoon.dixit.play.vo.Card;
import com.yoon.dixit.user.dto.UserDto;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Setter
@Getter
public class User {
    private String id;
//
//    @Builder.Default
//    private List<Card> cards = new ArrayList<>();
//
//    public void init() {
//        cards.clear();
//    }
//
//    public Card getSelectedCard() {
//        return cards.stream()
//                .filter(Card::isSelected)
//                .findFirst()
//                .orElseThrow(() -> new IllegalStateException("There is no selected card."));
//    }

    public UserDto toDto() {
        return UserDto.builder()
                .id(id)
                .build();
    }
}
