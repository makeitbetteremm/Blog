package com.tipsy.service;

import com.tipsy.domain.ResponseResult;
import com.tipsy.domain.vo.ArticleDto;
import com.tipsy.entity.Article;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 文章表 服务类
 * </p>
 *
 * @author lys
 * @since 2023-09-07
 */
public interface ArticleService extends IService<Article> {

    /**
     * 获取热门文章
     * @return
     */
    ResponseResult hotArticleList();

    /**
     * 获取文章信息
     * @return
     */
    ResponseResult articleList(Integer pageNum,Integer pageSize,Long categoryId);

    /**
     * 获取文章详情
     * @param id
     * @return
     */
    ResponseResult getArticleDetail(Long id);

    /**
     * 更新浏览量
     * @param id
     * @return
     */
    ResponseResult updateViewCount(Long id);

    /**
     * 新增文章
     * @param article
     * @return
     */
    ResponseResult add(ArticleDto article);

    /**
     * 模糊查询文章列表
     * @param pageNum
     * @param pageSize
     * @param title
     * @param summary
     * @return
     */
    ResponseResult articleListByFuzz(Integer pageNum, Integer pageSize, String title, String summary);

    /**
     * 根据id获取文章详情
     * @param id
     * @return
     */
    ResponseResult queryArticleById(Long id);

    /**
     * 修改文章
     * @param articleDto
     * @return
     */
    ResponseResult editArticle(ArticleDto articleDto);

    /**
     * 删除文章
     * @param id
     * @return
     */
    ResponseResult deleteArticle(Long id);
}
