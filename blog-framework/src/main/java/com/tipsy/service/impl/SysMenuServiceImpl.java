package com.tipsy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.tipsy.common.BeanCopyUtil;
import com.tipsy.common.constants.BlogConstant;
import com.tipsy.common.enums.AppHttpCodeEnum;
import com.tipsy.domain.ResponseResult;
import com.tipsy.domain.SystemException;
import com.tipsy.domain.vo.MenuTreeDto;
import com.tipsy.domain.vo.MenuVo;
import com.tipsy.entity.SysMenu;
import com.tipsy.repository.SysMenuMapper;
import com.tipsy.service.SysMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tipsy.service.SysRoleMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>
 * 菜单权限表 服务实现类
 * </p>
 *
 * @author lys
 * @since 2023-09-15
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {
    @Autowired
    private SysMenuMapper menuMapper;
    @Autowired
    private SysRoleMenuService sysRoleMenuService;

    @Override
    public List<String> selectPermissionByUserId(Long id) {
        if (1L == id) {

            List<SysMenu> menuList = getAdminPermissions(Arrays.asList("C","F"));
            List<String> permissions = menuList.stream().map(SysMenu::getPerms).collect(Collectors.toList());
            return permissions;
        }

        return menuMapper.selectPermissionByUserId(id);
    }

    @Override
    public List<MenuVo> selectRouterMenuTreeByUserId(Long userId) {

        List<SysMenu> menus;
        if (userId == 1L) {
            menus = getAdminPermissions(Arrays.asList("C","M"));;
        } else {
            menus = menuMapper.selectRouterMenuTreeByUserId(userId);
        }
        return buildMenuTree(menus);
    }

    @Override
    public ResponseResult menuList(String status, String menuName) {
        LambdaQueryWrapper<SysMenu> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.like(StringUtils.hasLength(menuName),SysMenu::getMenuName,menuName);
        queryWrapper.eq(StringUtils.hasLength(status),SysMenu::getStatus, BlogConstant.NORMAL);
        queryWrapper.orderByAsc(SysMenu::getId,SysMenu::getOrderNum);
        List<SysMenu> menuList = list(queryWrapper);
        return ResponseResult.okResult(menuList);
    }

    @Override
    public ResponseResult addMenu(SysMenu menu) {
        save(menu);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getMenu(Long id) {
        SysMenu menu = getById(id);
        return ResponseResult.okResult(menu);
    }

    @Override
    public ResponseResult editMenu(SysMenu menu) {
        if (Objects.isNull(menu.getParentId()) || menu.getParentId().equals(menu.getId())) {
            throw new SystemException(AppHttpCodeEnum.PARENT_MENU_ID);
        }
        updateById(menu);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteMenu(Long id) {
        checkChildrenMenu(id);
        removeById(id);
        return ResponseResult.okResult();
    }

    @Override
    public List<MenuVo> treeSelectMenu() {
        List<SysMenu> menuList;
        LambdaQueryWrapper<SysMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByAsc(SysMenu::getOrderNum);
        menuList = list(queryWrapper);
        List<MenuVo> menuVoList = buildMenuTree(menuList);

        return menuVoList;
    }

    @Override
    public ResponseResult treeSelectMenuByRole(Long roleId) {
        List<MenuVo> menuVoList = treeSelectMenu();
        List<Long> checkedKeys = new ArrayList<>();
        if  (1L==roleId) {
            setCheckedKeys(menuVoList,checkedKeys);
        } else {
            checkedKeys = sysRoleMenuService.queryMenuIdByRole(roleId);
        }
        MenuTreeDto menuTreeDto = new MenuTreeDto(menuVoList,checkedKeys);
        return ResponseResult.okResult(menuTreeDto);
    }

    private void setCheckedKeys(List<MenuVo> menuVoList, List<Long> checkedKeys) {
        menuVoList.forEach(menuVo -> {
            checkedKeys.add(menuVo.getId());
            if (!CollectionUtils.isEmpty(menuVo.getChildren())) {
                setCheckedKeys(menuVo.getChildren(),checkedKeys);
            }
        });

    }

    private void checkChildrenMenu (Long id) {
        LambdaQueryWrapper<SysMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysMenu::getParentId,id);
        List<SysMenu> menuList = list(queryWrapper);
        if (!CollectionUtils.isEmpty(menuList)) {
            throw new SystemException(AppHttpCodeEnum.CHILDREN_MENU_EXIST);
        }
    }

    private List<MenuVo> buildMenuTree(List<SysMenu> menuList) {
        List<MenuVo> menuVoList = BeanCopyUtil.copyBeanList(menuList,MenuVo.class);
        menuVoList.forEach(menuVo -> menuVo.setLabel(menuVo.getMenuName()));
        Map<Long,MenuVo> menuVoMap = menuVoList.stream()
                .collect(Collectors.toMap(MenuVo::getId, Function.identity()));
        for(MenuVo menuVo : menuVoList) {
            MenuVo parentMenu = menuVoMap.get(menuVo.getParentId());
            if (Objects.nonNull(parentMenu)) {
                if (parentMenu.getChildren() == null) {
                    parentMenu.setChildren(new ArrayList<>());
                }
                parentMenu.getChildren().add(menuVo);
            }
        }

        return menuVoList.stream().filter(m -> m.getParentId() == 0).collect(Collectors.toList());
    }


    private List<SysMenu> getAdminPermissions(List<String> menuTypes) {
        LambdaQueryWrapper<SysMenu> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.in(SysMenu::getMenuType,menuTypes);
        queryWrapper.eq(SysMenu::getStatus, BlogConstant.NORMAL);
        queryWrapper.orderByAsc(SysMenu::getOrderNum);
        List<SysMenu> menuList = list(queryWrapper);

        return menuList;
    }
}
