package com.tipsy.controller;

import com.tipsy.domain.ResponseResult;
import com.tipsy.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 分类表 前端控制器
 * </p>
 *
 * @author lys
 * @since 2023-09-08
 */
@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("getCategoryList")
    ResponseResult getCategoryList() {
       return categoryService.getCategoryList();
   }
}
