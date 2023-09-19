package com.tipsy.service;

import com.tipsy.entity.ArticleTag;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 文章标签关联表 服务类
 * </p>
 *
 * @author lys
 * @since 2023-09-16
 */
public interface ArticleTagService extends IService<ArticleTag> {

    /**
     * 根据文章id获取标签信息
     *
     * @param id
     * @return
     */
    List<Long> getTagIdsByArticleId(Long id);

    /**
     * 更新文章标签关系
     * @param articleId
     * @param tagIds
     */
    void updateArticleTags(Long articleId, List<Long> tagIds);
}
