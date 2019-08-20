package com.spring.boot.permission.service;


import com.spring.boot.common.bean.BaseBeanService;
import com.spring.boot.feign.pojo.permission.PermissionMenu;
import com.spring.boot.feign.pojo.permission.PermissionResource;
import com.spring.boot.feign.pojo.permission.PermissionRole;
import com.spring.boot.feign.pojo.permission.vo.PermissionMenuVO;
import com.spring.boot.permission.mapper.PermissionMenuMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yuderen
 * @version 2018/9/8 17:56
 */
@Service
public class PermissionMenuService extends BaseBeanService<PermissionMenuMapper, PermissionMenu> {

    @Autowired
    private PermissionMenuMapper permissionMenuMapper;
    @Autowired
    private PermissionRoleService permissionRoleService;
    @Autowired
    private PermissionResourceService permissionResourceService;

    public List<PermissionMenuVO> fetchPermissionMenuList(Long userId){
        List<PermissionMenuVO> menuList = fetchAllPermissionMenuList();
        if (permissionRoleService.isAdminRole(userId)){
            return menuList;
        }
        List<Long> subjectList = getSubjectList(userId);
        List<PermissionResource> resourceList = permissionResourceService.fetchResourceListBySubjectList(subjectList);
        Map<Long, PermissionResource> resourceMap = new HashMap();
        for (PermissionResource resource : resourceList){
            resourceMap.put(resource.getMenuId(),resource);
        }
        List<PermissionMenuVO> resultList = new ArrayList();
        initMenuResultList(resultList, menuList, resourceMap);
        return resultList;
    }

    private List<Long> getSubjectList(Long userId){
        List<PermissionRole> roleList = permissionRoleService.fetchPermissionRoleByUserId(userId);
        List<Long> subjectList = new ArrayList();
        for (PermissionRole permissionRole : roleList){
            subjectList.add(permissionRole.getGid());
        }
        subjectList.add(userId);
        return subjectList;
    }

    private void initMenuResultList(List<PermissionMenuVO> resultList, List<PermissionMenuVO> menuList,
                                    Map<Long, PermissionResource> resourceMap){
        for (PermissionMenuVO temp : menuList){
            String menuId = temp.getGid() + "";
            if (resourceMap.containsKey(menuId)){
                PermissionMenuVO itemMenu = new PermissionMenuVO();
                BeanUtils.copyProperties(temp, itemMenu);
                itemMenu.setPermission(resourceMap.get(menuId).getPermission());
                itemMenu.setChildren(new ArrayList());
                resultList.add(itemMenu);
                if (!temp.getChildren().isEmpty()){
                    initMenuResultList(itemMenu.getChildren(),temp.getChildren(),resourceMap);
                }
            }
        }
    }

    public List<PermissionMenuVO> fetchAllPermissionMenuList(){
        PermissionMenu permissionMenu = new PermissionMenu();
        permissionMenu.setAppType("1");
        return permissionMenuMapper.fetchPermissionMenuList(permissionMenu);
    }

}
