package com.tipsy.config;

import com.sun.org.apache.bcel.internal.generic.ARETURN;
import com.tipsy.common.RedisCache;
import com.tipsy.common.constants.BlogConstant;
import com.tipsy.entity.Article;
import com.tipsy.repository.ArticleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author lys
 * @Date 2023/9/14
 **/
@Component
public class ViewCountRunner implements CommandLineRunner {
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private RedisCache redisCache;

    @Override
    public void run(String... args) throws Exception {
        List<Article> articleList = articleMapper.selectList(null);
        Map<String,Integer> viewCountMap = articleList.stream()
                .collect(Collectors.toMap(a -> a.getId().toString(),a->a.getViewCount().intValue()));

        redisCache.setCacheMap(BlogConstant.ARTICLE_VIEW_COUNT,viewCountMap);
    }

}
