package com.tipsy.service;

import com.tipsy.entity.SysRoleMenu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 角色和菜单关联表 服务类
 * </p>
 *
 * @author lys
 * @since 2023-09-18
 */
public interface SysRoleMenuService extends IService<SysRoleMenu> {

    /**
     * 根据角色获取菜单权限
     * @param roleId
     * @return
     */
    List<Long> queryMenuIdByRole(Long roleId);

    /**
     * 跟新角色与菜单关系
     * @param roleId
     * @param menuIds
     */
    void updateRoleMenu(Long roleId, List<Long> menuIds);
}
