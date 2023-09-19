package com.tipsy.controller;

import com.tipsy.domain.ResponseResult;
import com.tipsy.domain.vo.ArticleDto;
import com.tipsy.service.ArticleService;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author lys
 * @Date 2023/9/16
 **/
@RestController
@RequestMapping("/content/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @PostMapping
    public ResponseResult add(@RequestBody ArticleDto article){
        return articleService.add(article);
    }

    @GetMapping("list")
    public ResponseResult articleListByFuzz(Integer pageNum, Integer pageSize,String title,String summary) {
        return articleService.articleListByFuzz(pageNum,pageSize,title,summary);
    }
    @GetMapping("/{id}")
    public ResponseResult queryArticleById(@PathVariable("id") Long id) {
        return articleService.queryArticleById(id);
    }
    @DeleteMapping("/{id}")
    public ResponseResult deleteArticle(@PathVariable("id") Long id) {
        return articleService.deleteArticle(id);
    }

    @PutMapping
    public ResponseResult editArticle(@RequestBody ArticleDto articleDto) {
        return articleService.editArticle(articleDto);
    }

}
