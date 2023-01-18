package com.yoon.dixit.socket.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yoon.dixit.user.dto.UserDto;
import lombok.*;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Setter
@Getter
public class SocketDto {
    private String userId;

    private String action;

    @JsonProperty("isLeader")
    private boolean isLeader;
}
