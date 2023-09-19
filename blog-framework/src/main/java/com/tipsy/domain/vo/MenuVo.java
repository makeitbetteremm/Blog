package com.tipsy.domain.vo;

import com.tipsy.entity.SysMenu;
import lombok.Data;
import lombok.EqualsAndHashCode;
import sun.util.resources.ga.LocaleNames_ga;

import java.util.List;


/**
 * @author lys
 * @Date 2023/9/16
 **/
@Data
@EqualsAndHashCode(callSuper = true)
public class MenuVo extends SysMenu {
     private List<MenuVo> children;
     private String label;
}
