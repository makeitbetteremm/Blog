package com.tipsy.controller;

import com.tipsy.domain.ResponseResult;
import com.tipsy.domain.vo.RoleVo;
import com.tipsy.entity.SysRole;
import com.tipsy.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

/**
 * <p>
 * 角色信息表 前端控制器
 * </p>
 *
 * @author lys
 * @since 2023-09-18
 */
@RestController
@RequestMapping("/system/role")
public class SysRoleController {
    @Autowired
    private SysRoleService roleService;

    @GetMapping("/list")
    public ResponseResult roleList(Integer pageNum, Integer pageSize,String roleName,String status) {
        return roleService.roleList(pageNum,pageSize,roleName,status);
    }

    @PutMapping("/changeStatus")
    public ResponseResult changeStatus(@RequestBody SysRole role) {
        return roleService.changeStatus(role.getId() ,role.getStatus());
    }

    @PostMapping
    public ResponseResult addRole(@RequestBody RoleVo roleVo) {
        return roleService.addRole(roleVo);
    }
    @GetMapping("/{id}")
    public ResponseResult getRole(@PathVariable("id") Long id) {
        return roleService.getRole(id);
    }

    @PutMapping
    public ResponseResult editRole(@RequestBody RoleVo roleVo) {
        return roleService.editRole(roleVo);
    }

    @GetMapping("/listAllRole")
    public ResponseResult listAllRole() {
        return ResponseResult.okResult(roleService.listAllRole());
    }



    @DeleteMapping("/{id}")
    public ResponseResult deleteRole(@PathVariable("id") Long id) {
        return roleService.deleteRole(id);
    }
}
