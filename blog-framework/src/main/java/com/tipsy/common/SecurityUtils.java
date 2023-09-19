package com.tipsy.common;

import com.tipsy.entity.LoginUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Objects;

/**
 * @author lys
 * @Date 2023/9/13
 **/
public class SecurityUtils {
    /**
     * 获取用户
     **/
    public static LoginUser getLoginUser()
    {
        Authentication authentication = getAuthentication();
        if (Objects.isNull(authentication)) {
            return null;
        }
        return (LoginUser) getAuthentication().getPrincipal();
    }

    /**
     * 获取Authentication
     */
    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public static Boolean isAdmin(){
        LoginUser loginUser = getLoginUser();
        Long id = null;
        if (Objects.nonNull(loginUser)) {
            id = loginUser.getUser().getId();
        }
        return id != null && 1L == id;
    }

    public static Long getUserId() {
        Long id = -1L;
        LoginUser loginUser = getLoginUser();
        if (Objects.nonNull(loginUser)) {
            id = loginUser.getUser().getId();
        }
        return id;
    }
}
