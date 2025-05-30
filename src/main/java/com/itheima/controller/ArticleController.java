package com.itheima.controller;

import com.itheima.pojo.Article;
import com.itheima.pojo.PageBean;
import com.itheima.pojo.Result;
import com.itheima.service.ArticleService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/article")
public class ArticleController {
    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @PostMapping
    public Result<Void> addArticle(@RequestBody @Validated(Article.Add.class) Article article) {
        return articleService.addArticle(article);
    }

    @PutMapping
    public Result<Void> updateArticle(@RequestBody @Validated(Article.Update.class) Article article) {
        return articleService.updateArticle(article);
    }

    @GetMapping("/detail")
    public Result<Article> detail(@RequestParam Integer id) {
        return articleService.detail(id);
    }

    @DeleteMapping
    public Result<Void> deleteArticle(@RequestParam Integer id) {
        return articleService.deleteArticle(id);
    }

    @GetMapping
    public Result<PageBean<Article>> list(
            Integer pageNum,
            Integer pageSize,
            @RequestParam(required = false) final Integer categoryId,
            @RequestParam(required = false) final String state
    ) {
        PageBean<Article> pageBean = articleService.list(pageNum, pageSize, categoryId, state);
        return Result.success(pageBean);
    }

}
