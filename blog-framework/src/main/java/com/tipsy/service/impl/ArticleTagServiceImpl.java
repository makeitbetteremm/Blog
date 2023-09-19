package com.tipsy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.tipsy.entity.ArticleTag;
import com.tipsy.repository.ArticleTagMapper;
import com.tipsy.service.ArticleTagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * 文章标签关联表 服务实现类
 * </p>
 *
 * @author lys
 * @since 2023-09-16
 */
@Service
public class ArticleTagServiceImpl extends ServiceImpl<ArticleTagMapper, ArticleTag> implements ArticleTagService {
    @Autowired
    private ArticleTagMapper articleTagMapper;

    @Override
    public List<Long> getTagIdsByArticleId(Long id) {
        LambdaQueryWrapper<ArticleTag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ArticleTag::getArticleId,id);
        List<ArticleTag> articleTags = list(queryWrapper);

        return articleTags.stream().map(ArticleTag::getTagId).collect(Collectors.toList());
    }

    @Override
    public void updateArticleTags(Long articleId, List<Long> tagIds) {
        LambdaQueryWrapper<ArticleTag> articleTagLambdaQueryWrapper = new LambdaQueryWrapper<>();
        articleTagLambdaQueryWrapper.eq(ArticleTag::getArticleId,articleId);
        this.remove(articleTagLambdaQueryWrapper);
        if (CollectionUtils.isEmpty(tagIds)) {
            return;
        }
        List<ArticleTag> articleTags = tagIds.stream().map(t->new ArticleTag(articleId,t)).collect(Collectors.toList());
        saveBatch(articleTags);
    }
}
