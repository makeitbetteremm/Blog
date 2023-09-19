package com.tipsy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.tipsy.entity.SysUserRole;
import com.tipsy.repository.SysUserRoleMapper;
import com.tipsy.service.SysUserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户和角色关联表 服务实现类
 * </p>
 *
 * @author lys
 * @since 2023-09-19
 */
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements SysUserRoleService {

    @Override
    public void updateUserRole(Long userId, List<Long> roleIdList) {
        LambdaQueryWrapper<SysUserRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUserRole::getUserId,userId);
        remove(queryWrapper);

        if (!CollectionUtils.isEmpty(roleIdList)) {
            List<SysUserRole> userRoles = roleIdList.stream()
                    .map(r -> new SysUserRole(userId,r)).collect(Collectors.toList());
            saveBatch(userRoles);
        }
    }
}
