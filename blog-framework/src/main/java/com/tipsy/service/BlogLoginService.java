package com.tipsy.service;

import com.tipsy.domain.ResponseResult;
import com.tipsy.domain.vo.BlogLoginVo;
import com.tipsy.entity.SysUser;

/**
 * @author lys
 * @Date 2023/9/11
 **/
public interface BlogLoginService {
    /**
     * 用户登陆
     * @param user
     * @return
     */
    ResponseResult login(SysUser user);

    /**
     * 登录失败
     * @return
     */
    ResponseResult logout();
}
