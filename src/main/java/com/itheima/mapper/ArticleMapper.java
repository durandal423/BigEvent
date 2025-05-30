package com.itheima.mapper;

import com.itheima.pojo.Article;
import com.itheima.pojo.PageBean;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ArticleMapper {
    @Insert("INSERT INTO article(title, content, cover_img, state, category_id, create_user, create_time, update_time) " +
            "VALUES(#{title},#{content},#{coverImg},#{state},#{categoryId},#{createUser},#{createTime},#{updateTime})")
    void addArticle(Article article);

    List<Article> list(Integer userId, Integer categoryId, String state);

    @Update("UPDATE article SET " +
            "title=#{title},content=#{content},cover_img=#{coverImg},state=#{state},category_id=#{categoryId},update_time=#{updateTime} " +
            "WHERE id=#{id}")
    void updateArticle(Article article);

    @Select("SELECT * FROM article WHERE id=#{id}")
    Article findById(Integer id);

    @Delete("DELETE FROM article WHERE id=#{id}")
    void deleteArticle(Integer id);
}
