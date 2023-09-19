package com.tipsy.domain.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author lys
 * @Date 2023/9/8
 **/
@Data
public class HotArticleVo implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String title;
    private Integer viewCount;

}
