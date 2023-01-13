package com.yoon.dixit.user.dto;


import lombok.*;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Setter
@Getter
public class SignUpDto {
    private String username;

    private String password;
}
