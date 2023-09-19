package com.tipsy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tipsy.common.BeanCopyUtil;
import com.tipsy.common.SecurityUtils;
import com.tipsy.common.enums.AppHttpCodeEnum;
import com.tipsy.domain.ResponseResult;
import com.tipsy.domain.SystemException;
import com.tipsy.domain.vo.EditUserDto;
import com.tipsy.domain.vo.PageVo;
import com.tipsy.domain.vo.UserVo;
import com.tipsy.entity.SysRole;
import com.tipsy.entity.SysUser;
import com.tipsy.entity.SysUserRole;
import com.tipsy.entity.UserInfo;
import com.tipsy.repository.SysUserMapper;
import com.tipsy.service.SysRoleService;
import com.tipsy.service.SysUserRoleService;
import com.tipsy.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author lys
 * @since 2023-09-13
 */
@Service
public class UserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private SysRoleService roleService;
    @Autowired
    private SysUserRoleService userRoleService;

    @Override
    public ResponseResult userInfo() {
        Long userId = SecurityUtils.getUserId();
        SysUser user = getById(userId);
        UserInfo userInfo = BeanCopyUtil.copyBean(user,UserInfo.class);
        return ResponseResult.okResult(userInfo);
    }

    @Override
    public ResponseResult updateUserInfo(SysUser user) {
        updateById(user);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult register(SysUser user) {
        //对数据进行非空判断
        if(!StringUtils.hasText(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.USERNAME_NOT_NULL);
        }
        if(!StringUtils.hasText(user.getPassword())){
            throw new SystemException(AppHttpCodeEnum.PASSWORD_NOT_NULL);
        }
        if(!StringUtils.hasText(user.getEmail())){
            throw new SystemException(AppHttpCodeEnum.EMAIL_NOT_NULL);
        }
        if(!StringUtils.hasText(user.getNickName())){
            throw new SystemException(AppHttpCodeEnum.NICKNAME_NOT_NULL);
        }
        //对数据进行是否存在的判断
        if(userNameExist(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        }
        if(nickNameExist(user.getNickName())){
            throw new SystemException(AppHttpCodeEnum.NICKNAME_EXIST);
        }

        String password = passwordEncoder.encode(user.getPassword());
        user.setPassword(password);
        save(user);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult listUser(Integer pageNum, Integer pageSize, String userName, String phonenumber, String status) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasLength(userName),SysUser::getUserName,userName);
        queryWrapper.like(StringUtils.hasLength(phonenumber),SysUser::getPhonenumber,phonenumber);
        queryWrapper.eq(StringUtils.hasLength(status),SysUser::getStatus,status);

        Page<SysUser> userPage = new Page<>(pageNum,pageSize);
        page(userPage,queryWrapper);

        PageVo pageVo = new PageVo(userPage.getRecords(),userPage.getTotal());

        return ResponseResult.okResult(pageVo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult addUser(UserVo userVo) {
        checkAddUser(userVo);

        SysUser user = BeanCopyUtil.copyBean(userVo,SysUser.class);
        user.setId(IdWorker.getId(user));
        save(user);

        List<Long> roleIds = userVo.getRoleIds();
        List<SysUserRole> sysUserRoles = roleIds.stream()
                .map(r -> new SysUserRole(user.getId(),r)).collect(Collectors.toList());
        userRoleService.saveBatch(sysUserRoles);

        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getUserById(Long id) {
        List<SysRole> roleList = roleService.listAllRole();

        LambdaQueryWrapper<SysUserRole> userRoleLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userRoleLambdaQueryWrapper.eq(SysUserRole::getUserId,id);
        List<Long> roleIds = userRoleService.list(userRoleLambdaQueryWrapper).stream()
                .map(SysUserRole::getRoleId).collect(Collectors.toList());

        SysUser user = getById(id);

        EditUserDto editUserDto = new EditUserDto(roleIds,roleList,user);

        return ResponseResult.okResult(editUserDto);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult editUser(UserVo userVo) {
        checkAddUser(userVo);

        List<Long> roleIdList = userVo.getRoleIds();
        SysUser user = BeanCopyUtil.copyBean(userVo,SysUser.class);

        updateById(user);

        userRoleService.updateUserRole(user.getId(),roleIdList);

        return ResponseResult.okResult();
    }

    private void checkAddUser(UserVo userVo) {
        if (!StringUtils.hasLength(userVo.getUserName())) {
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getUserName,userVo.getUserName());
        List<SysUser> users;
        users = list(queryWrapper);
        for(SysUser user : users) {
            if (!user.getId().equals(userVo.getId())) {
                throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
            }
        }

        if (StringUtils.hasLength(userVo.getPhonenumber())) {
            queryWrapper.clear();
            queryWrapper.eq(SysUser::getPhonenumber,userVo.getPhonenumber());
            users = list(queryWrapper);
            for(SysUser user : users) {
                if ( !user.getId().equals(userVo.getId())) {
                    throw new SystemException(AppHttpCodeEnum.PHONENUMBER_EXIST);
                }
            }
        }

        if (StringUtils.hasLength(userVo.getEmail())) {
            queryWrapper.clear();
            queryWrapper.eq(SysUser::getEmail,userVo.getEmail());
            users = list(queryWrapper);
            for(SysUser user : users) {
                if (!user.getId().equals(userVo.getId())) {
                    throw new SystemException(AppHttpCodeEnum.EMAIL_EXIST);
                }
            }
        }

    }

    private boolean nickNameExist(String nickName) {
        LambdaQueryWrapper<SysUser> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userLambdaQueryWrapper.eq(SysUser::getNickName,nickName);

        return count(userLambdaQueryWrapper) > 0;
    }

    private boolean userNameExist(String userName) {
        LambdaQueryWrapper<SysUser> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userLambdaQueryWrapper.eq(SysUser::getUserName,userName);

        return count(userLambdaQueryWrapper) > 0;
    }
}
