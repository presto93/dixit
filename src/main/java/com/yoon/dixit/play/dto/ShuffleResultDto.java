package com.yoon.dixit.play.dto;

import com.yoon.dixit.play.vo.Card;
import lombok.*;

import java.util.List;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Setter
@Getter
public class ShuffleResultDto {
    private List<Card> cards;
    private boolean first;
}
