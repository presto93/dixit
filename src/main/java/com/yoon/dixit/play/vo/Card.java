package com.yoon.dixit.play.vo;

import com.yoon.dixit.user.vo.User;
import lombok.*;

import java.io.File;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Setter
@Getter
@ToString
public class Card {
    private int id;
    private File file;
    private boolean target;
}
