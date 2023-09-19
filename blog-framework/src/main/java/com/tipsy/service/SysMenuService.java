package com.tipsy.service;

import com.tipsy.domain.ResponseResult;
import com.tipsy.domain.vo.MenuVo;
import com.tipsy.entity.SysMenu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 菜单权限表 服务类
 * </p>
 *
 * @author lys
 * @since 2023-09-15
 */
public interface SysMenuService extends IService<SysMenu> {

    /**
     * 根据userid获取权限
     * @param id
     * @return
     */
    List<String> selectPermissionByUserId(Long id);

    /**
     * 获取用户权限列表
     * @param userId
     * @return
     */
    List<MenuVo> selectRouterMenuTreeByUserId(Long userId);

    /**
     * 模糊查询所有菜单
     * @param status
     * @param menuName
     * @return
     */
    ResponseResult menuList(String status, String menuName);

    /**
     * 添加菜单
     * @param menu
     * @return
     */
    ResponseResult addMenu(SysMenu menu);

    /**
     * 根据id获取menu
     * @param id
     * @return
     */
    ResponseResult getMenu(Long id);

    /**
     * 修改菜单
     * @param menu
     * @return
     */
    ResponseResult editMenu(SysMenu menu);

    /**
     * 删除菜单
     * @param id
     * @return
     */
    ResponseResult deleteMenu(Long id);

    /**
     * 获取菜单树结构
     * @return
     */
    List<MenuVo> treeSelectMenu();

    /**
     * 根据角色获取菜单树结构
     * @param roleId
     * @return
     */
    ResponseResult treeSelectMenuByRole(Long roleId);
}
