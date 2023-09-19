package com.tipsy.domain.vo;

import com.tipsy.entity.SysRole;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author lys
 * @Date 2023/9/18
 **/
@Data
@EqualsAndHashCode(callSuper = true)
public class RoleVo extends SysRole {
    List<Long> menuIds;
}
