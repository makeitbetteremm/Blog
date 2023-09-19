package com.tipsy.domain.vo;

import com.tipsy.entity.SysRole;
import com.tipsy.entity.SysUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author lys
 * @Date 2023/9/19
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EditUserDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<Long> roleIds;
    private List<SysRole> roles;
    private SysUser user;
}
