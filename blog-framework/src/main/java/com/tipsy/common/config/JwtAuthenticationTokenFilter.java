package com.tipsy.common.config;

import com.alibaba.fastjson.JSON;
import com.tipsy.common.JwtUtil;
import com.tipsy.common.RedisCache;
import com.tipsy.common.WebUtils;
import com.tipsy.common.enums.AppHttpCodeEnum;
import com.tipsy.domain.ResponseResult;
import com.tipsy.entity.LoginUser;
import com.tipsy.entity.SysUser;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * @author lys
 * @Date 2023/9/11
 **/
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    @Autowired
    private RedisCache redisCache;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("token");

        // token为空不做校验
        if (!StringUtils.hasText(token)) {
            filterChain.doFilter(request,response);
            return;
        }

        Claims claims = null;
        // 解码token为userId
        try {
            claims = JwtUtil.parseJWT(token);
        } catch (Exception e) {
            e.printStackTrace();
            ResponseResult responseResult = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
            WebUtils.renderString(response, JSON.toJSONString(responseResult));
            return;
        }
        String userId = claims.getSubject();

        // 从redis获取用户信息
        SysUser sysUser =  redisCache.getCacheObject("bloglogin:"+ userId);
        if (Objects.isNull(sysUser)) {
            ResponseResult responseResult = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
            WebUtils.renderString(response, JSON.toJSONString(responseResult));
            return;
        }

        LoginUser loginUser = new LoginUser(sysUser);
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(loginUser,null,null);
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);


        filterChain.doFilter(request,response);
    }
}
