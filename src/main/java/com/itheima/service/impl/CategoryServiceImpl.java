package com.itheima.service.impl;

import com.itheima.mapper.CategoryMapper;
import com.itheima.pojo.Category;
import com.itheima.pojo.Result;
import com.itheima.service.CategoryService;
import com.itheima.utils.ThreadLocalUtil;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryMapper categoryMapper;

    public CategoryServiceImpl(CategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
    }

    @Override
    public Result<List<Category>> list() {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        List<Category> categories = categoryMapper.list(userId);
        return Result.success(categories);
    }

    @Override
    public Result<Void> addCategory(Category category) {
        String newCategoryName = category.getCategoryName();
        // 已有相同文章名
        if (categoryMapper.exitCategory(newCategoryName)) {
            return Result.error("该分类已存在");
        }
        // 获取id
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");

        // 赋值
        category.setCreateUser(userId);
        category.setCreateTime(LocalDateTime.now());
        category.setUpdateTime(LocalDateTime.now());

        categoryMapper.addCategory(category);
        return Result.success();
    }

    @Override
    public Result<Void> update(Category category) {
        String oldCategoryName = categoryMapper.findNameById(category.getId());
        String newCategoryName = category.getCategoryName();
        // 与原文章明不同且已有相同文章名
        if (categoryMapper.exitCategory(newCategoryName) && !oldCategoryName.equals(newCategoryName)) {
            return Result.error("该分类已存在");
        }
        // 本次更新时间
        category.setUpdateTime(LocalDateTime.now());
        categoryMapper.update(category);
        return Result.success();
    }

    @Override
    public Result<Void> delete(Integer id) {
        categoryMapper.delete(id);
        return Result.success();
    }

    @Override
    public Result<Category> detail(Integer id) {
        Category category = categoryMapper.findById(id);
        return Result.success(category);
    }
}
