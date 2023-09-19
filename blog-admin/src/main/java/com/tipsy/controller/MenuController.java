package com.tipsy.controller;

import com.tipsy.domain.ResponseResult;
import com.tipsy.entity.SysMenu;
import com.tipsy.service.SysMenuService;
import org.junit.jupiter.params.ParameterizedTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.web.bind.annotation.*;

/**
 * @author lys
 * @Date 2023/9/18
 **/
@RestController
@RequestMapping("system/menu")
public class MenuController {
    @Autowired
    private SysMenuService menuService;

    @GetMapping("/list")
    public ResponseResult menuList(String status,String menuName) {
        return menuService.menuList(status,menuName);
    }

    @PostMapping
    public ResponseResult addMenu(@RequestBody SysMenu menu) {
        return menuService.addMenu(menu);
    }

    @GetMapping("/{id}")
    public ResponseResult getMenu(@PathVariable("id") Long id) {
        return menuService.getMenu(id);
    }

    @PutMapping
    public ResponseResult editMenu(@RequestBody SysMenu menu) {
        return menuService.editMenu(menu);
    }

    @DeleteMapping("/{menuId}")
    public ResponseResult deleteMenu(@PathVariable("menuId") Long id) {
        return menuService.deleteMenu(id);
    }

    @GetMapping("/treeselect")
    public ResponseResult treeSelect() {
        return ResponseResult.okResult(menuService.treeSelectMenu());
    }

    @GetMapping("/roleMenuTreeselect/{id}")
    public ResponseResult roleMenuTreeSelect(@PathVariable("id") Long roleId) {
        return menuService.treeSelectMenuByRole(roleId);
    }
}
