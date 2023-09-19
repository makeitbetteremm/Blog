package com.tipsy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tipsy.common.BeanCopyUtil;
import com.tipsy.domain.ResponseResult;
import com.tipsy.domain.vo.PageVo;
import com.tipsy.domain.vo.RoleVo;
import com.tipsy.entity.SysRole;
import com.tipsy.entity.SysRoleMenu;
import com.tipsy.repository.SysRoleMapper;
import com.tipsy.service.SysRoleMenuService;
import com.tipsy.service.SysRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 角色信息表 服务实现类
 * </p>
 *
 * @author lys
 * @since 2023-09-15
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    @Autowired
    private SysRoleMapper roleMapper;
    @Autowired
    private SysRoleMenuService roleMenuService;

    @Override
    public List<String> selectRoleKeyByUserId(Long id) {
        if (id == 1L) {
            return new ArrayList<>(Arrays.asList("admin"));
        }

       return roleMapper.selectRoleKeyByUserId(id);
    }

    @Override
    public ResponseResult roleList(Integer pageNum, Integer pageSize, String roleName, String status) {
        LambdaQueryWrapper<SysRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasLength(roleName),SysRole::getRoleName,roleName);
        queryWrapper.eq(StringUtils.hasLength(status),SysRole::getStatus,status);
        queryWrapper.orderByAsc(SysRole::getRoleSort);

        Page<SysRole> page = new Page<>(pageNum,pageSize);
        page(page,queryWrapper);

        PageVo pageVo = new PageVo(page.getRecords(),page.getTotal());

        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult changeStatus(Long id, String status) {
        LambdaUpdateWrapper<SysRole> updateWrapper = new LambdaUpdateWrapper();
        updateWrapper.eq(SysRole::getId,id);
        updateWrapper.set(SysRole::getStatus,status);
        update(updateWrapper);
        return ResponseResult.okResult();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult addRole(RoleVo roleVo) {
        SysRole role = BeanCopyUtil.copyBean(roleVo,SysRole.class);
        role.setId(IdWorker.getId());
        save(role);

        if (!CollectionUtils.isEmpty(roleVo.getMenuIds())) {
            List<SysRoleMenu> roleMenuList = roleVo.getMenuIds()
                    .stream().map(m -> new SysRoleMenu(role.getId(),m)).collect(Collectors.toList());
            roleMenuService.saveBatch(roleMenuList);
        }
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getRole(Long id) {
        SysRole role = getById(id);
        return ResponseResult.okResult(role);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult editRole(RoleVo roleVo) {
        roleMenuService.updateRoleMenu(roleVo.getId(),roleVo.getMenuIds());

        SysRole role = BeanCopyUtil.copyBean(roleVo,SysRole.class);
        updateById(role);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteRole(Long id) {
        removeById(id);
        return ResponseResult.okResult();
    }

    @Override
    public List<SysRole> listAllRole() {
        LambdaQueryWrapper<SysRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByAsc(SysRole::getRoleSort);
        return list(queryWrapper);
    }
}
