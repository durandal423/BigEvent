package com.itheima.service.impl;

import com.itheima.mapper.UserMapper;
import com.itheima.pojo.Result;
import com.itheima.pojo.User;
import com.itheima.service.UserService;
import com.itheima.utils.JwtUtil;
import com.itheima.utils.Md5Util;
import com.itheima.utils.ThreadLocalUtil;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final StringRedisTemplate stringRedisTemplate;

    public UserServiceImpl(UserMapper userMapper,StringRedisTemplate stringRedisTemplate) {
        this.userMapper = userMapper;
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public Result<Void> register(String username, String password) {
        // 查询用户
        User user = userMapper.findByUserName(username);
        if (user == null) {
            // 没有被占用 注册
            // 密码加密
            String md5String = Md5Util.getMD5String(password + username);
            // 添加
            userMapper.addUser(username, md5String, LocalDateTime.now());
            return Result.success();
        } else {
            // 被占用了
            return Result.error("用户名已被占用");
        }
    }

    @Override
    public Result<Void> update(User user) {
        final Map<String, Object> map = ThreadLocalUtil.get();
        final Integer id = (Integer) map.get("id");
        if(user.getId().equals(id)){
            user.setUpdateTime(LocalDateTime.now());
            System.out.println("user updateTime = "+ user.getUpdateTime());
            userMapper.update(user);
            return Result.success();
        }else{
            return Result.error("非本人id");
        }
    }

    @Override
    public Result<Void> updateAvatar(String avatarUrl) {
        final Map<String, Object> map = ThreadLocalUtil.get();
        final Integer id = (Integer) map.get("id");
        userMapper.updateAvatar(avatarUrl, id, LocalDateTime.now());
        return Result.success();
    }

    @Override
    public Result<String> login(String username, String password) {
        // 根据用户名查询用户
        User loginUser = userMapper.findByUserName(username);
        // 判断用户是否存在 密码是否正确
        if (loginUser == null || !Md5Util.getMD5String(password + username).equals(loginUser.getPassword())) {
            return Result.error("用户名或密码错误");
        }
        // 登录成功
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", loginUser.getId());
        claims.put("username", loginUser.getUsername());
        String token = JwtUtil.genToken(claims);
        // 把token存储到redis中
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        operations.set("heima:UserToken:"+loginUser.getId().toString(), token, 1, TimeUnit.HOURS);
        return Result.success(token);
    }

    @Override
    public Result<Void> updatePassword(Map<String, String> params, String token) {
        // 校验参数
        String oldPwd = params.get("old_pwd");
        String newPwd = params.get("new_pwd");
        String rePwd = params.get("re_pwd");

        // 校验新密码格式
        if (newPwd.length() < 5 || newPwd.length() > 16) {
            return Result.error("新密码格式不正确");
        }

        if (!StringUtils.hasLength(oldPwd) || !StringUtils.hasLength(newPwd) || !StringUtils.hasLength(rePwd)) {
            return Result.error("缺少必要的参数");
        }

        // 校验原密码是否正确
        // 调用userService根据用户名拿到原密码, 再和old_pwd比对
        final Map<String, Object> map = ThreadLocalUtil.get();
        final String username = (String) map.get("username");
        User loginUser = userMapper.findByUserName(username);
        String oldMd5String = Md5Util.getMD5String(oldPwd + username);
        if (!loginUser.getPassword().equals(oldMd5String)) {
            return Result.error("原密码填写不正确");
        }

        // 检查新旧密码是否重复
        String newMd5String = Md5Util.getMD5String(newPwd + username);
        if (oldMd5String.equals(newMd5String)) {
            return Result.error("新旧密码不能一致");
        }

        // newPwd和rePwd是否一样
        if (!newPwd.equals(rePwd)) {
            return Result.error("两次填写的新密码不一致");
        }

        // 调用Service完成密码更新
        Integer id = (Integer) map.get("id");
        userMapper.updatePwd(newMd5String, id, LocalDateTime.now());
        // 删除redis中对应的token
        stringRedisTemplate.delete("heima:UserToken:"+loginUser.getId().toString());
        return Result.success();
    }


    @Override
    public Result<User> userInfo() {
        final Map<String, Object> map = ThreadLocalUtil.get();
        final String username = (String) map.get("username");
        User user = userMapper.findByUserName(username);
        return Result.success(user);
    }
}
