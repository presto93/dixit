package com.yoon.dixit.play.vo;

import lombok.*;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Setter
@Getter
@ToString
public class Card {
    private int id;
    private String filename;
    private boolean isTarget;
}
