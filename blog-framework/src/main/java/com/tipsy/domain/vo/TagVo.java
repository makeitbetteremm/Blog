package com.tipsy.domain.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * @author lys
 * @Date 2023/9/16
 **/
@Data
public class TagVo implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 标签名
     */
    private String name;
    /**
     * 备注
     */
    private String remark;
}
