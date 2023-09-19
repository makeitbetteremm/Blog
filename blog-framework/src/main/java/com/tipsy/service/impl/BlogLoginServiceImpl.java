package com.tipsy.service.impl;

import com.tipsy.common.BeanCopyUtil;
import com.tipsy.common.JwtUtil;
import com.tipsy.common.RedisCache;
import com.tipsy.common.SecurityUtils;
import com.tipsy.domain.ResponseResult;
import com.tipsy.domain.vo.BlogLoginVo;
import com.tipsy.entity.LoginUser;
import com.tipsy.entity.SysUser;
import com.tipsy.entity.UserInfo;
import com.tipsy.service.BlogLoginService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author lys
 * @Date 2023/9/11
 **/
@Service
public class BlogLoginServiceImpl implements BlogLoginService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private RedisCache redisCache;

    @Override
    public ResponseResult login(SysUser user) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        if (Objects.isNull(authentication)) {
            throw new RuntimeException("用户名或者密码错误");
        }

        LoginUser loginUser = (LoginUser) authentication.getPrincipal();

        // 缓存user数据
        redisCache.setCacheObject("bloglogin:"+loginUser.getUser().getId(),loginUser.getUser());

        // 返回token和用户信息
        String token = JwtUtil.createJWT(loginUser.getUser().getId().toString());
        UserInfo userInfo = BeanCopyUtil.copyBean(loginUser.getUser(),UserInfo.class);
        BlogLoginVo blogLoginVo = new BlogLoginVo(token,userInfo);
        return ResponseResult.okResult(blogLoginVo);
    }

    @Override
    public ResponseResult logout() {

        redisCache.deleteObject("bloglogin:"+ SecurityUtils.getUserId().toString());
        return ResponseResult.okResult();
    }
}
