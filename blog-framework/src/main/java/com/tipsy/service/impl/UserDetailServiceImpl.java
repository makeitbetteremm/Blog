package com.tipsy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.tipsy.entity.LoginUser;
import com.tipsy.entity.SysUser;
import com.tipsy.repository.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author lys
 * @Date 2023/9/11
 **/
@Service
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LambdaQueryWrapper<SysUser> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userLambdaQueryWrapper.eq(SysUser::getUserName,username);
        SysUser user = sysUserMapper.selectOne(userLambdaQueryWrapper);

        if (Objects.isNull(user)) {
            throw new UsernameNotFoundException("用户不存在");
        }


        return new LoginUser(user);
    }
}
