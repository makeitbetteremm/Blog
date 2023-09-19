package com.tipsy.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;
import java.io.Serializable;

/**
 * @author lys
 * @Date 2023/9/9
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageVo implements Serializable {
    private static final long serialVersionUID = 1L;

    private List rows;
    private Long total;
}
