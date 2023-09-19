package com.tipsy.service;

import com.tipsy.domain.ResponseResult;
import com.tipsy.domain.vo.UserVo;
import com.tipsy.entity.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author lys
 * @since 2023-09-13
 */
public interface UserService extends IService<SysUser> {

    /**
     * 获取用户信息
     * @return
     */
    ResponseResult userInfo();

    /**
     * 更新用户信息
     * @param user
     * @return
     */
    ResponseResult updateUserInfo(SysUser user);

    /**
     * 注册
     * @param user
     * @return
     */
    ResponseResult register(SysUser user);

    /**
     * 分页查询用户信息
     * @param pageNum
     * @param pageSize
     * @param userName
     * @param phonenumber
     * @param status
     * @return
     */
    ResponseResult listUser(Integer pageNum, Integer pageSize, String userName, String phonenumber, String status);

    /**
     * 增加用户
     * @return
     */
    ResponseResult addUser(UserVo userVo);

    /**
     * 获取用户信息
     * @param id
     * @return
     */
    ResponseResult getUserById(Long id);

    /**
     * 修改用户信息
     * @param userVo
     * @return
     */
    ResponseResult editUser(UserVo userVo);
}
