package com.yoon.dixit.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yoon.dixit.user.enums.PlayingStatus;
import lombok.*;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Setter
@Getter
public class UserDto {
    private String id;

    @JsonProperty("isLeader")
    private boolean isLeader;

    private PlayingStatus playingStatus;
}
