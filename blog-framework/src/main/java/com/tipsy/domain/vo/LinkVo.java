package com.tipsy.domain.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * @author lys
 * @Date 2023/9/11
 **/
@Data
public class LinkVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String name;

    private String logo;

    private String description;

    /**
     * 网站地址
     */
    private String address;

    private String status;

}
