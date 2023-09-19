package com.tipsy.domain.vo;

import com.tipsy.entity.UserInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author lys
 * @Date 2023/9/15
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminUserInfoVo implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<String> permissions;

    private List<String> roles;

    private UserInfo user;
}
