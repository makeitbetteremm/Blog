package com.tipsy.repository;

import com.tipsy.entity.SysRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 角色信息表 Mapper 接口
 * </p>
 *
 * @author lys
 * @since 2023-09-06
 */
public interface SysRoleMapper extends BaseMapper<SysRole> {

    /**
     * 根据userid获取角色信息
     * @param id
     * @return
     */
    List<String> selectRoleKeyByUserId(Long id);

}
