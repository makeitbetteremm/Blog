package com.tipsy.repository;

import com.tipsy.entity.Article;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 文章表 Mapper 接口
 * </p>
 *
 * @author lys
 * @since 2023-09-06
 */
public interface ArticleMapper extends BaseMapper<Article> {
    /**
     * 获取正式发布的文章分类id
     * @return
     */
    List<Long> normalCategoryIdList();
}
