package com.itheima.controller;

import com.itheima.pojo.Result;
import com.itheima.pojo.User;
import com.itheima.service.UserService;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.URL;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {
    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("register")
    public Result<Void> register(@Pattern(regexp = "^\\S{5,16}$") String username,
                                 @Pattern(regexp = "^\\S{5,16}$") String password) {
        return userService.register(username, password);
    }

    @PostMapping("/login")
    public Result<String> login(@Pattern(regexp = "^\\S{5,16}$") String username,
                                @Pattern(regexp = "^\\S{5,16}$") String password) {
        return userService.login(username, password);
    }

    @GetMapping("/userInfo")
    public Result<User> userInfo() {
        return userService.userInfo();
    }

    @PutMapping("/update")
    public Result<Void> update(@RequestBody @Validated User user) {
        return userService.update(user);
    }

    @PatchMapping("/updateAvatar")
    public Result<Void> updateAvatar(@RequestParam @URL String avatarUrl) {
        return userService.updateAvatar(avatarUrl);
    }

    @PatchMapping("/updatePwd")
    public Result<Void> updatePwd(@RequestBody Map<String, String> params, @RequestHeader("Authorization") String token) {
        return userService.updatePassword(params,token);
    }
}
