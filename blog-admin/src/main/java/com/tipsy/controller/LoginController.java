package com.tipsy.controller;

import com.tipsy.common.BeanCopyUtil;
import com.tipsy.common.SecurityUtils;
import com.tipsy.common.enums.AppHttpCodeEnum;
import com.tipsy.domain.ResponseResult;
import com.tipsy.domain.SystemException;
import com.tipsy.domain.vo.AdminUserInfoVo;
import com.tipsy.domain.vo.MenuVo;
import com.tipsy.entity.LoginUser;
import com.tipsy.entity.SysUser;
import com.tipsy.entity.UserInfo;
import com.tipsy.service.BlogLoginService;
import com.tipsy.service.SysMenuService;
import com.tipsy.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author lys
 * @Date 2023/9/15
 **/
@RestController
public class LoginController {
    @Autowired
    private BlogLoginService loginService;

    @Autowired
    private SysMenuService menuService;

    @Autowired
    private SysRoleService roleService;


    @PostMapping("/user/login")
    public ResponseResult login(@RequestBody SysUser user){
        if(!StringUtils.hasText(user.getUserName())){
            //提示 必须要传用户名
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        return loginService.login(user);
    }

    @GetMapping("getInfo")
    public ResponseResult<AdminUserInfoVo> getInfo(){
        //获取当前登录的用户
        LoginUser loginUser = SecurityUtils.getLoginUser();
        //根据用户id查询权限信息
        List<String> perms = menuService.selectPermissionByUserId(loginUser.getUser().getId());
        //根据用户id查询角色信息
        List<String> roleKeyList = roleService.selectRoleKeyByUserId(loginUser.getUser().getId());

        //获取用户信息
        SysUser user = loginUser.getUser();
        UserInfo userInfoVo = BeanCopyUtil.copyBean(user, UserInfo.class);
        //封装数据返回

        AdminUserInfoVo adminUserInfoVo = new AdminUserInfoVo(perms,roleKeyList,userInfoVo);
        return ResponseResult.okResult(adminUserInfoVo);
    }

    @GetMapping("getRouters")
    public ResponseResult getRouters(){
        Long userId = SecurityUtils.getUserId();
        //查询menu 结果是tree的形式
        List<MenuVo> menus = menuService.selectRouterMenuTreeByUserId(userId);
        //封装数据返回
        return ResponseResult.okResult(menus);
    }

    @PostMapping("/user/logout")
    public ResponseResult logout(){
        return loginService.logout();
    }
}
