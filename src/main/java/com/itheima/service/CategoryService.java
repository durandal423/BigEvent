package com.itheima.service;

import com.itheima.pojo.Category;
import com.itheima.pojo.Result;

import java.util.List;

public interface CategoryService {

    // 文章分类列表
    Result<List<Category>> list();

    // 新增文章分类
    Result<Void> addCategory(Category category);

    // 更新分类
    Result<Void> update(Category category);

    // 删除分类
    Result<Void> delete(Integer id);

    // 文章分类详情
    Result<Category> detail(Integer id);
}
