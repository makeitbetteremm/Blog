package com.tipsy.service;

import com.tipsy.domain.ResponseResult;
import com.tipsy.domain.vo.CategoryVo;
import com.tipsy.entity.Category;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 分类表 服务类
 * </p>
 *
 * @author lys
 * @since 2023-09-08
 */
public interface CategoryService extends IService<Category> {
    /**
     * 获取正式发布的文章的分类
     * @return
     */
    ResponseResult getCategoryList();

    /**
     * 获取所有分类
     * @return
     */
    List<CategoryVo> listAllCategory();

    /**
     * 获取分类查询信息
     * @param pageNum
     * @param pageSize
     * @param name
     * @param status
     * @return
     */
    ResponseResult listCategories(Integer pageNum, Integer pageSize, String name, String status);

    /**
     * 增加分类
     * @param category
     * @return
     */
    ResponseResult addCategory(CategoryVo category);

    /**
     * 获取分类信息
     * @param id
     * @return
     */
    ResponseResult getCategory(Long id);

    /**
     * 修改分类信息
     * @param categoryVo
     * @return
     */
    ResponseResult editCategory(CategoryVo categoryVo);
}
