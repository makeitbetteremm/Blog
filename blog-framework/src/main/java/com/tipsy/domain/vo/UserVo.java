package com.tipsy.domain.vo;

import com.tipsy.entity.SysRole;
import com.tipsy.entity.SysUser;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author lys
 * @Date 2023/9/19
 **/
@Data
@EqualsAndHashCode(callSuper = true)
public class UserVo extends SysUser {
    List<Long> roleIds;
}
