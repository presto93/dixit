package com.yoon.dixit.user.vo;

import com.yoon.dixit.user.dto.UserDto;
import com.yoon.dixit.user.enums.PlayingStatus;
import lombok.*;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Setter
@Getter
public class User {
    private String id;

    private PlayingStatus playingStatus;

    boolean isLeader;

    public UserDto toDto() {
        return UserDto.builder()
                .id(id)
                .isLeader(isLeader)
                .build();
    }
}
