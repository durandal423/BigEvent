package com.itheima.mapper;

import com.itheima.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDateTime;

@Mapper
public interface UserMapper {
    // 根据用户名查询用户
    @Select("SELECT * FROM big_event.user WHERE username=#{username}")
    User findByUserName(String username);

    // 添加
    @Insert("INSERT INTO user(username, password, create_time, update_time)" +
            " VALUES(#{username},#{password},#{now},#{now})")
    void addUser(String username, String password, LocalDateTime now);

    @Update("UPDATE user SET nickname=#{nickname},email=#{email},update_time=#{updateTime} WHERE id=#{id}")
    void update(User user);

    @Update("UPDATE user SET user_pic=#{avatarUrl},update_time=#{now} WHERE id=#{id}")
    void updateAvatar(String avatarUrl, Integer id, LocalDateTime now);

    @Update("UPDATE user SET password=#{md5String},update_time=#{now} WHERE id=#{id}")
    void updatePwd(String md5String, Integer id, LocalDateTime now);
}
