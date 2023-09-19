package com.tipsy.common.config;

import com.tipsy.common.RedisCache;
import com.tipsy.common.constants.BlogConstant;
import com.tipsy.domain.vo.ArticleListVo;
import com.tipsy.entity.Article;
import com.tipsy.service.ArticleService;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author lys
 * @Date 2023/9/14
 **/
@Component
public class UpdateViewCountJob {
    @Autowired
    private RedisCache redisCache;

    @Autowired
    private ArticleService articleService;

    @Scheduled(cron = "0/5 * * * * ?")
    public void  updateViewCount() {
        Map<String,Integer> viewCountMap = redisCache.getCacheMap(BlogConstant.ARTICLE_VIEW_COUNT);
        List<Article> articleList = viewCountMap.entrySet().stream()
                .map(e -> new Article(Long.valueOf(e.getKey()),e.getValue())).collect(Collectors.toList());
        articleService.updateBatchById(articleList);
    }
}
