package com.tipsy.service;

import com.tipsy.entity.SysUserRole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 用户和角色关联表 服务类
 * </p>
 *
 * @author lys
 * @since 2023-09-19
 */
public interface SysUserRoleService extends IService<SysUserRole> {

    /**
     * 更新用户角色关系
     * @param userId
     * @param roleIdList
     */
    void updateUserRole(Long userId, List<Long> roleIdList);
}
