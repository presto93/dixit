package com.yoon.dixit.user.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yoon.dixit.play.vo.Card;
import com.yoon.dixit.user.dto.UserDto;
import com.yoon.dixit.user.enums.PlayingStatus;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Setter
@Getter
public class User {
    private String id;

    private PlayingStatus playingStatus;

    private LocalDateTime lastAccessed;

    public UserDto toDto() {
        return UserDto.builder()
                .id(id)
                .build();
    }
}
