package com.tipsy.entity;

import lombok.Data;

/**
 * @author lys
 * @Date 2023/9/11
 **/
@Data
public class UserInfo {
    /**
     * 主键
     */
    private Long id;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 头像
     */
    private String avatar;

    private String sex;

    private String email;
}
