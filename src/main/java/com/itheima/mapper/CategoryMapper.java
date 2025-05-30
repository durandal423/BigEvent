package com.itheima.mapper;

import com.itheima.pojo.Category;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CategoryMapper {
    @Select("SELECT * FROM category WHERE create_user=#{userId}")
    List<Category> list(Integer userId);

    @Insert("INSERT INTO category(category_name, category_alias, create_user, create_time, update_time)" +
            " VALUES(#{categoryName},#{categoryAlias},#{createUser},#{createTime},#{updateTime})")
    void addCategory(Category category);

    @Select("SELECT EXISTS(SELECT 1 FROM category WHERE category_name=#{categoryName})")
    boolean exitCategory(String categoryName);

    @Select("SELECT * FROM category WHERE id=#{id}")
    Category findById(Integer id);

    @Update("UPDATE category SET category_name=#{categoryName},category_alias=#{categoryAlias},update_time=#{updateTime}" +
            " WHERE id=#{id}")
    void update(Category category);

    @Delete("DELETE FROM category WHERE id=#{id}")
    void delete(Integer id);

    @Select("SELECT category_name FROM category WHERE id=#{id}")
    String findNameById(Integer id);
}
