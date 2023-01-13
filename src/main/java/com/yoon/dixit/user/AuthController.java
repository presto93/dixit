package com.yoon.dixit.user;

import com.yoon.dixit.user.dto.SignUpDto;
import com.yoon.dixit.user.service.UserValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserValidationService userValidationService;

    @PostMapping("/sign-up")
    public void signUp(SignUpDto signupDto) {
        userValidationService.saveUser(signupDto);
    }
}
