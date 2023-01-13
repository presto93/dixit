package com.yoon.dixit.user;

import com.yoon.dixit.user.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController("/user")
@CrossOrigin("*")
public class UserController {
    private final UserService userService;

    @PostMapping("/login")
    public UserDto startGame(@RequestParam String userId) {
        return userService.login(userId).toDto();
    }

    @GetMapping("/shuffle")
    public void shuffleOrder() {
        userService.shuffleOrder();
    }

    @GetMapping("/clear")
    public void clearUsers() {
        userService.clearUsers();
    }

}
