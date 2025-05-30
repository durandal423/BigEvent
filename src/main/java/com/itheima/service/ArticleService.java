package com.itheima.service;

import com.itheima.pojo.Article;
import com.itheima.pojo.PageBean;
import com.itheima.pojo.Result;

public interface ArticleService {

    // 新增文章
    Result<Void> addArticle(Article article);

    // 文章列表查询(分页)
    PageBean<Article> list(Integer pageNum, Integer pageSize, Integer categoryId, String state);

    // 更新文章
    Result<Void> updateArticle(Article article);

    // 获取文章详情
    Result<Article> detail(Integer id);

    // 删除文章
    Result<Void> deleteArticle(Integer id);
}
