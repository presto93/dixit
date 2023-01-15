package com.yoon.dixit.user;

import com.yoon.dixit.user.dto.LoginDto;
import com.yoon.dixit.user.dto.UserDto;
import com.yoon.dixit.user.service.UserService;
import com.yoon.dixit.user.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
@CrossOrigin("*")
public class UserController {
    private final UserService userService;
    private final UsersService usersService;

    @PostMapping("/login")
    public UserDto startGame(@RequestBody LoginDto loginDto) {
        return userService.login(loginDto.getUserId()).toDto();
    }

    @PutMapping("/logout")
    public void logout(@RequestBody LoginDto loginDto) {
        System.out.println("logout " + loginDto.getUserId());
        userService.logout(loginDto.getUserId());
    }
//
//    @PutMapping("/change-leader")
//    public void shuffleOrder() {
//        usersService.;
//    }

    @PutMapping("/clear")
    public void clearUsers() {
        usersService.clearUsers();
    }

}
