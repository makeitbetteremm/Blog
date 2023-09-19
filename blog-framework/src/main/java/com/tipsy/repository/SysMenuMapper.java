package com.tipsy.repository;

import com.tipsy.entity.SysMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 菜单权限表 Mapper 接口
 * </p>
 *
 * @author lys
 * @since 2023-09-06
 */
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    /**
     * 根据userid获取用户权限
     * @param id
     * @return
     */
    List<String> selectPermissionByUserId(Long id);

    /**
     * 根据userid获取用户授权菜单
     * @param userId
     * @return
     */
    List<SysMenu> selectRouterMenuTreeByUserId(Long userId);
}
