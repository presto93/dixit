package com.yoon.dixit.user;

import com.yoon.dixit.user.dto.LoginDto;
import com.yoon.dixit.user.dto.UserDto;
import com.yoon.dixit.user.service.UserService;
import com.yoon.dixit.user.service.UsersService;
import com.yoon.dixit.user.vo.User;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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

    @PutMapping("/kick-out")
    public List<UserDto> kickOutUser(@RequestBody LoginDto loginDto) {
        userService.logout(loginDto.getUserId());
        return usersService.getAll();
    }

    @PutMapping("/change-leader")
    public List<UserDto> changeLeader(String userId) {
        usersService.changeLeader(userId);

        return usersService.getAll();
    }

    @PutMapping("/clear")
    public void clearUsers() {
        usersService.clearUsers();
    }

    @GetMapping
    public List<UserDto> getAllUsers() {
        return usersService.getAll();
    }

    @GetMapping("/{userId}")
    public UserDto getUserById(@PathVariable String userId) {
        return usersService.get(userId).toDto();
    }
}
