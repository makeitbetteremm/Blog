package com.tipsy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tipsy.common.BeanCopyUtil;
import com.tipsy.common.constants.BlogConstant;
import com.tipsy.domain.ResponseResult;
import com.tipsy.domain.vo.CategoryVo;
import com.tipsy.domain.vo.PageVo;
import com.tipsy.entity.Category;
import com.tipsy.repository.ArticleMapper;
import com.tipsy.repository.CategoryMapper;
import com.tipsy.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>
 * 分类表 服务实现类
 * </p>
 *
 * @author lys
 * @since 2023-09-08
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public ResponseResult getCategoryList() {
        // 获取正式发布文章的分类
        List<Long> categoryIds = articleMapper.normalCategoryIdList();

        // 查询分类列表
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(Category::getId,categoryIds);
        queryWrapper.eq(Category::getStatus,0);
        List<Category> categoryList = list(queryWrapper);

        List<CategoryVo> categories = BeanCopyUtil.copyBeanList(categoryList,CategoryVo.class);
        return ResponseResult.okResult(categories);
    }

    @Override
    public List<CategoryVo> listAllCategory() {
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Category::getStatus, BlogConstant.NORMAL);
        List<Category> list = list(wrapper);
        List<CategoryVo> categoryVos = BeanCopyUtil.copyBeanList(list, CategoryVo.class);
        return categoryVos;
    }

    @Override
    public ResponseResult listCategories(Integer pageNum, Integer pageSize, String name, String status) {
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasLength(name),Category::getName,name);
        queryWrapper.eq(StringUtils.hasLength(status),Category::getStatus,status);

        Page<Category> page = new Page<>(pageNum,pageSize);
        page(page,queryWrapper);

        PageVo pageVo = new PageVo(page.getRecords(),page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult addCategory(CategoryVo category) {
        Category categoryEntity = BeanCopyUtil.copyBean(category,Category.class);
        save(categoryEntity);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getCategory(Long id) {
        Category category = getById(id);
        CategoryVo categoryVo = BeanCopyUtil.copyBean(category,CategoryVo.class);
        return ResponseResult.okResult(categoryVo);
    }

    @Override
    public ResponseResult editCategory(CategoryVo categoryVo) {
        Category category = BeanCopyUtil.copyBean(categoryVo,Category.class);
        updateById(category);
        return ResponseResult.okResult();
    }
}
