package com.yoon.dixit.user;

import com.yoon.dixit.user.dto.UserDto;
import com.yoon.dixit.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
@CrossOrigin("*")
public class UserController {
    private final UserService userService;

    @PostMapping("/login")
    public UserDto startGame(@RequestBody String userId) {
        return userService.login(userId).toDto();
    }

    @PutMapping("/logout")
    public void logout(@RequestBody String userId) {
        System.out.println("logout " + userId);
        userService.logout(userId);
    }

    @PutMapping("/change-leader")
    public void shuffleOrder() {
        userService.changeLeader();
    }

    @PutMapping("/clear")
    public void clearUsers() {
        userService.clearUsers();
    }

}
