package com.tipsy.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.tipsy.domain.ResponseResult;
import com.tipsy.domain.vo.UserVo;
import com.tipsy.entity.SysRole;
import com.tipsy.entity.SysUser;
import com.tipsy.entity.SysUserRole;
import com.tipsy.service.SysRoleService;
import com.tipsy.service.SysUserRoleService;
import com.tipsy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author lys
 * @Date 2023/9/19
 **/
@RestController
@RequestMapping("system/user")
public class UserController {
    @Autowired
    private UserService userService;


    @GetMapping("/list")
    public ResponseResult listUser(Integer pageNum
            , Integer pageSize,String userName,String phonenumber,String status) {
        return userService.listUser(pageNum,pageSize,userName,phonenumber,status);
    }

    @PostMapping
    public ResponseResult addUser(@RequestBody UserVo userVo) {
        return userService.addUser(userVo);
    }

    @DeleteMapping("/{id}")
    public ResponseResult deleteUser(@PathVariable("id") Long id) {
        userService.removeById(id);
        return ResponseResult.okResult();
    }

    @GetMapping("/{id}")
    public ResponseResult getUserById(@PathVariable("id") Long id) {
        return userService.getUserById(id);
    }

    @PutMapping
    public ResponseResult editUser(@RequestBody UserVo userVo) {
        return userService.editUser(userVo);
    }

}
