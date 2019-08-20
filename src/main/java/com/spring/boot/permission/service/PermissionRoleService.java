package com.spring.boot.permission.service;

import com.spring.boot.common.bean.BaseBeanService;
import com.spring.boot.feign.pojo.permission.PermissionMenu;
import com.spring.boot.feign.pojo.permission.PermissionResource;
import com.spring.boot.feign.pojo.permission.PermissionRole;
import com.spring.boot.feign.pojo.permission.vo.PermissionMenuVO;
import com.spring.boot.permission.mapper.PermissionRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author yuderen
 * @version 2018/9/8 18:01
 */
@Service
public class PermissionRoleService extends BaseBeanService<PermissionRoleMapper, PermissionRole> {

    @Autowired
    private PermissionRoleMapper permissionRoleMapper;
    @Autowired
    private PermissionMenuService permissionMenuService;
    @Autowired
    private PermissionResourceService permissionResourceService;

    public List<PermissionMenuVO> fetchPermissionRoleResource(PermissionRole permissionRole){
        List<PermissionMenuVO> itemMenuList = permissionMenuService.fetchAllPermissionMenuList();
        List<PermissionResource> resourceList = permissionResourceService.fetchPermissionResourceListByRoleId(permissionRole.getGid());
        Set<Long> validResourceSet = new HashSet();
        for (PermissionResource resource : resourceList){
            validResourceSet.add(resource.getMenuId());
        }
        initItemMenuChecked(itemMenuList, validResourceSet);
        return itemMenuList;
    }

    public List<PermissionRole> fetchPermissionRoleByUserId(Long userId){
        return permissionRoleMapper.fetchPermissionRoleByUserId(userId);
    }

    public Boolean isAdminRole(Long userId){
        List<PermissionRole> roleList = fetchPermissionRoleByUserId(userId);
        for (PermissionRole permissionRole : roleList){
            if (permissionRole.getRoleType() == 1){
                return Boolean.TRUE;
            }
        }
        return false;
    }

    public Integer permissionRoleResource(String[] operIds, Long roleId){
        String auth = "1|2|4|8";
        List<PermissionResource> resourceList = new ArrayList();
        PermissionResource permissionResource = null;
        Integer permission = 0;
        for (String operId : operIds){
            if (auth.indexOf(operId) == -1){
                permissionResource = initPermissionRoleResource(operId, roleId);
                resourceList.add(permissionResource);
                permission = 0;
            } else {
                permission += Integer.parseInt(operId);
                permissionResource.setPermission(permission);
            }
        }
        return permissionResourceService.permissionRoleResource(resourceList, roleId);
    }

    private PermissionResource initPermissionRoleResource(String menuId, Long roleId){
        PermissionResource permissionResource = new PermissionResource();
        PermissionMenu permissionMenu = permissionMenuService.fetchRecordByGid(Long.parseLong(menuId));
        PermissionRole permissionRole = this.fetchRecordByGid(roleId);

        permissionResource.setAppType("1");
        permissionResource.setSubjectType("role");
        permissionResource.setSubjectId(permissionRole.getGid());
        permissionResource.setSubjectName(permissionRole.getRoleName());
        permissionResource.setMenuId(permissionMenu.getGid());
        permissionResource.setMenuName(permissionMenu.getMenuName());
        return permissionResource;
    }


    private void initItemMenuChecked(List<PermissionMenuVO> itemMenuList, Set<Long> validResourceSet){
        for (PermissionMenuVO itemMenu : itemMenuList){
            itemMenu.setChecked(validResourceSet.contains(itemMenu.getGid()));
            if (!itemMenu.getChildren().isEmpty()){
                initItemMenuChecked(itemMenu.getChildren(), validResourceSet);
            }
        }
    }

}
