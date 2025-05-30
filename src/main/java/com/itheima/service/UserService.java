package com.itheima.service;

import com.itheima.pojo.Result;
import com.itheima.pojo.User;

import java.util.Map;

public interface UserService {

    // 注册用户
    Result<Void> register(String username, String password);

    // 更新
    Result<Void> update(User user);

    // 更新头像
    Result<Void> updateAvatar(String avatarUrl);

    // 用户登录
    Result<String> login(String username, String password);

    // 更新密码
    Result<Void> updatePassword(Map<String, String> params, String token);

    Result<User> userInfo();
}
