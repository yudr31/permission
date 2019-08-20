package com.spring.boot.permission.service;

import com.spring.boot.feign.pojo.permission.PermissionMenu;
import com.spring.boot.feign.pojo.permission.PermissionResource;
import com.spring.boot.feign.pojo.permission.PermissionRole;
import com.spring.boot.feign.pojo.permission.vo.PermissionMenuVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yuderen
 * @version 2018/9/8 18:05
 */
@Service
public class PermissionService {

    @Autowired
    private PermissionMenuService permissionMenuService;
    @Autowired
    private PermissionRoleService permissionRoleService;
    @Autowired
    private PermissionResourceService permissionResourceService;

    public Integer addPermissionMenu(PermissionMenu permissionMenu){
        return permissionMenuService.insertSelective(permissionMenu);
    }

    public Integer addPermissionRole(PermissionRole permissionRole){
        return permissionRoleService.insertSelective(permissionRole);
    }

    public Integer addPermissionResource(PermissionResource permissionResource){
        return permissionResourceService.insertSelective(permissionResource);
    }

    public List<PermissionMenuVO> fetchPermissionMenuList(Long userId){
        return permissionMenuService.fetchPermissionMenuList(userId);
    }

//    public List<PermissionResource> fetchPermissionResource(){
//        return permissionResourceService.selectRecordList(new PermissionResource());
//    }
}
