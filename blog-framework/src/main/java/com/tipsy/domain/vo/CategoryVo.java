package com.tipsy.domain.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author lys
 * @Date 2023/9/9
 **/
@Data
public class CategoryVo implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    /**
     * 描述
     */
    private String description;
    private String status;
}
