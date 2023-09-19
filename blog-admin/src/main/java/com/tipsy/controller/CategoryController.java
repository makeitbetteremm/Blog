package com.tipsy.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import com.tipsy.common.BeanCopyUtil;
import com.tipsy.common.WebUtils;
import com.tipsy.common.enums.AppHttpCodeEnum;
import com.tipsy.domain.ResponseResult;
import com.tipsy.domain.vo.CategoryVo;
import com.tipsy.domain.vo.ExcelCategoryVo;
import com.tipsy.entity.Category;
import com.tipsy.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author lys
 * @Date 2023/9/16
 **/
@RestController
@RequestMapping("/content/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/listAllCategory")
    public ResponseResult listAllCategory(){
        List<CategoryVo> list = categoryService.listAllCategory();
        return ResponseResult.okResult(list);
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response){
        try {
            //设置下载文件的请求头
            WebUtils.setDownLoadHeader("分类.xlsx",response);
            //获取需要导出的数据
            List<Category> categoryVos = categoryService.list();

            BeanCopyUtil BeanCopyUtils;
            List<ExcelCategoryVo> excelCategoryVos = BeanCopyUtil.copyBeanList(categoryVos, ExcelCategoryVo.class);
            //把数据写入到Excel中
            EasyExcel.write(response.getOutputStream(), ExcelCategoryVo.class).sheet("分类导出")
                    .doWrite(excelCategoryVos);

        } catch (Exception e) {
            //如果出现异常也要响应json
            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
            WebUtils.renderString(response, JSON.toJSONString(result));
        }
    }

    @GetMapping("/list")
    public ResponseResult listCategories(Integer pageNum, Integer pageSize,String name,String status) {
        return categoryService.listCategories(pageNum,pageSize,name,status);
    }

    @PostMapping
    public ResponseResult addCategory(@RequestBody CategoryVo categoryVo) {
        return categoryService.addCategory(categoryVo);
    }

    @GetMapping("/{id}")
    public ResponseResult getCategory(@PathVariable("id") Long id) {
        return categoryService.getCategory(id);
    }

    @PutMapping
    public ResponseResult editCategory(@RequestBody CategoryVo categoryVo) {
        return categoryService.editCategory(categoryVo);
    }

    @DeleteMapping("/{id}")
    public ResponseResult deleteCategory(@PathVariable("id") Long id) {
        categoryService.removeById(id);
        return ResponseResult.okResult();
    }
}
