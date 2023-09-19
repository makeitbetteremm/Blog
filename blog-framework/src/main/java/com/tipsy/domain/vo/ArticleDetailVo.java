package com.tipsy.domain.vo;

import io.swagger.models.auth.In;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author lys
 * @Date 2023/9/11
 **/
@Data
public class ArticleDetailVo implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 标题
     */
    private String title;

    /**
     * 文章摘要
     */
    private String summary;

    /**
     * 所属分类ID
     */
    private Long categoryId;
    /**
     * 所属分类名
     */
    private String categoryName;
    /**
     * 文章内容
     */
    private String content;

    /**
     * 缩略图
     */
    private String thumbnail;


    /**
     * 访问量
     */
    private Integer viewCount;

    private Date createTime;

}
