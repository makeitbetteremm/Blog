package com.tipsy.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author lys
 * @Date 2023/9/18
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuTreeDto implements Serializable {
    private static final long serialVersionUID = 1L;
    List<MenuVo> menus;
    List<Long> checkedKeys;
}
