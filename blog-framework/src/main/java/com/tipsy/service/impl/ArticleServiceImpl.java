package com.tipsy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tipsy.common.BeanCopyUtil;
import com.tipsy.common.RedisCache;
import com.tipsy.common.constants.BlogConstant;
import com.tipsy.domain.ResponseResult;
import com.tipsy.domain.vo.*;
import com.tipsy.entity.Article;
import com.tipsy.entity.ArticleTag;
import com.tipsy.entity.Category;
import com.tipsy.entity.Tag;
import com.tipsy.repository.ArticleMapper;
import com.tipsy.repository.CategoryMapper;
import com.tipsy.service.ArticleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tipsy.service.ArticleTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 文章表 服务实现类
 * </p>
 *
 * @author lys
 * @since 2023-09-07
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private RedisCache redisCache;
    @Autowired
    private ArticleTagService articleTagService;

    @Override
    public ResponseResult hotArticleList() {
        //获取文章并排序
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Article::getStatus,0);
        queryWrapper.orderByAsc(Article::getViewCount);

        // 分页查十条
        Page<Article> page = new Page<>(1,10);
        page(page,queryWrapper);
        if (CollectionUtils.isEmpty(page.getRecords())) {
            return ResponseResult.okResult(new ArrayList<>());
        }
        updateArticleViewCount(page.getRecords());
        List<HotArticleVo> hotArticles = BeanCopyUtil.copyBeanList(page.getRecords(),HotArticleVo.class);
        return ResponseResult.okResult(hotArticles);
    }

    @Override
    public ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId) {
        // 查询文章列表，根据置顶降序
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(Objects.nonNull(categoryId),Article::getCategoryId,categoryId);
        queryWrapper.eq(Article::getStatus, BlogConstant.NORMAL);
        queryWrapper.orderByAsc(Article::getIsTop);

        Page<Article> articlePage = new Page<>(pageNum,pageSize);
        page(articlePage,queryWrapper);
        updateArticleViewCount(articlePage.getRecords());

        List<ArticleListVo> articleLists = BeanCopyUtil.copyBeanList(articlePage.getRecords(),ArticleListVo.class);

        // 获取分类名称
        List<Long> categoryIds = articlePage.getRecords().stream()
                .map(Article::getCategoryId).distinct().filter(id->Objects.nonNull(id)).collect(Collectors.toList());
        Map<Long,String> categoryMap = categoryMapper.selectBatchIds(categoryIds).stream()
                .collect(Collectors.toMap(Category::getId,Category::getName,(k1,k2)->k1));

        articleLists.forEach(a->{
            a.setCategoryName(categoryMap.get(a.getCategoryId()));
        });

        // 转换为分页数据
        PageVo pageVo = new PageVo(articleLists,articlePage.getTotal());

        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult getArticleDetail(Long id) {
        Article article = getById(id);
        updateArticleViewCount(new ArrayList<>(Arrays.asList(article)));

        Category category = categoryMapper.selectById(article.getCategoryId());

        ArticleDetailVo articleDetailVo = BeanCopyUtil.copyBean(article,ArticleDetailVo.class);
        if (category != null) {
            articleDetailVo.setCategoryName(category.getName());
        }

        return ResponseResult.okResult(articleDetailVo);
    }

    @Override
    public ResponseResult updateViewCount(Long id) {
        redisCache.incrementCacheMapValue(BlogConstant.ARTICLE_VIEW_COUNT,id.toString(),1);
        return ResponseResult.okResult();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult add(ArticleDto articleDto) {
        //添加 博客
        Article article = BeanCopyUtil.copyBean(articleDto, Article.class);
        save(article);


        List<ArticleTag> articleTags = articleDto.getTags().stream()
                .map(tagId -> new ArticleTag(articleDto.getId(), tagId))
                .collect(Collectors.toList());

        //添加 博客和标签的关联
        articleTagService.saveBatch(articleTags);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult articleListByFuzz(Integer pageNum, Integer pageSize, String title, String summary) {

        // 查询文章列表，根据置顶降序
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.like(StringUtils.hasLength(title),Article::getTitle,title);
        queryWrapper.like(StringUtils.hasLength(summary),Article::getSummary,summary);
        queryWrapper.orderByAsc(Article::getIsTop);

        Page<Article> articlePage = new Page<>(pageNum,pageSize);
        page(articlePage,queryWrapper);
        List<ArticleDto> articleDtoList = BeanCopyUtil.copyBeanList(articlePage.getRecords(),ArticleDto.class);

        // 转换为分页数据
        PageVo pageVo = new PageVo(articleDtoList,articlePage.getTotal());

        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult queryArticleById(Long id) {
        Article article = getById(id);
        ArticleDto articleDto = BeanCopyUtil.copyBean(article,ArticleDto.class);
        List<Long> tagIdList = articleTagService.getTagIdsByArticleId(article.getId());
        articleDto.setTags(tagIdList);
        return ResponseResult.okResult(articleDto);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult editArticle(ArticleDto articleDto) {
        List<Long> tagIds = articleDto.getTags();
        Article article = BeanCopyUtil.copyBean(articleDto,Article.class);
        updateById(article);

        articleTagService.updateArticleTags(article.getId(),tagIds);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteArticle(Long id) {
        removeById(id);
        return ResponseResult.okResult();
    }

    /**
     * 从redis更新围观数据
     * @param articleList
     */
    private void updateArticleViewCount(List<Article> articleList) {
        articleList.forEach(article -> article.setViewCount(redisCache.getCacheMapValue(BlogConstant.ARTICLE_VIEW_COUNT,article.getId().toString())));
    }
}
