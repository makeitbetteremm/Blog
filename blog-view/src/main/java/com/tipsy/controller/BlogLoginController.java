package com.tipsy.controller;

import com.tipsy.common.annotation.SystemLog;
import com.tipsy.common.enums.AppHttpCodeEnum;
import com.tipsy.domain.ResponseResult;
import com.tipsy.domain.SystemException;
import com.tipsy.entity.SysUser;
import com.tipsy.service.BlogLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lys
 * @Date 2023/9/11
 **/
@RestController
public class BlogLoginController {
    @Autowired
    private BlogLoginService blogLoginService;

    @SystemLog(businessName = "登录")
    @PostMapping("/login")
    public ResponseResult login(@RequestBody SysUser user) {
        if (!StringUtils.hasText(user.getUserName())) {
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }

        return blogLoginService.login(user);
    }

    @PostMapping("/logout")
    public ResponseResult logout() {
        return blogLoginService.logout();
    }

}
