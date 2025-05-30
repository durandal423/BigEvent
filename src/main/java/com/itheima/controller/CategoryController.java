package com.itheima.controller;

import com.itheima.pojo.Category;
import com.itheima.pojo.Result;
import com.itheima.service.CategoryService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public Result<List<Category>> list() {
        return categoryService.list();
    }

    @PostMapping
    public Result<Void> addCategory(@RequestBody @Validated(Category.Add.class) Category category) {
        return categoryService.addCategory(category);
    }

    @PutMapping
    public Result<Void> update(@RequestBody @Validated(Category.Update.class) Category category) {
        return categoryService.update(category);
    }

    @GetMapping("/detail")
    public Result<Category> detail(Integer id) {
        return categoryService.detail(id);
    }

    @DeleteMapping
    public Result<Void> delete(Integer id) {
        return categoryService.delete(id);
    }

}
