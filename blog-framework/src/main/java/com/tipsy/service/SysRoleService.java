package com.tipsy.service;

import com.tipsy.domain.ResponseResult;
import com.tipsy.domain.vo.RoleVo;
import com.tipsy.entity.SysRole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 角色信息表 服务类
 * </p>
 *
 * @author lys
 * @since 2023-09-15
 */
public interface SysRoleService extends IService<SysRole> {

    /**
     * 根据userid获取角色
     * @param id
     * @return
     */
    List<String> selectRoleKeyByUserId(Long id);

    /**
     * 分页获取角色列表
     * @param pageNum
     * @param pageSize
     * @param roleName
     * @param status
     * @return
     */
    ResponseResult roleList(Integer pageNum, Integer pageSize, String roleName, String status);

    /**
     * 改变角色状态
     * @param id
     * @param status
     * @return
     */
    ResponseResult changeStatus(Long id, String status);

    /**
     * 添加角色
     * @param roleVo
     * @return
     */
    ResponseResult addRole(RoleVo roleVo);

    /**
     * 获取角色信息
     * @param id
     * @return
     */
    ResponseResult getRole(Long id);

    /**
     * 编辑角色
     * @param roleVo
     * @return
     */
    ResponseResult editRole(RoleVo roleVo);

    /**
     * 删除角色
     * @param id
     * @return
     */
    ResponseResult deleteRole(Long id);

    /**
     * 获取所有角色
     * @return
     */
    List<SysRole> listAllRole();
}
