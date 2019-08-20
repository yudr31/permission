package com.spring.boot.permission.service;

import com.spring.boot.common.bean.BaseBeanService;
import com.spring.boot.feign.pojo.permission.PermissionResource;
import com.spring.boot.permission.mapper.PermissionResourceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author yuderen
 * @version 2018/9/8 18:11
 */
@Service
public class PermissionResourceService extends BaseBeanService<PermissionResourceMapper, PermissionResource> {

    @Autowired
    private PermissionResourceMapper permissionResourceMapper;

    public List<PermissionResource> fetchPermissionResourceListByRoleId(Long roleId){
        PermissionResource permissionResource = new PermissionResource();
        permissionResource.setSubjectId(roleId);
        return super.fetchRecordList(permissionResource);
    }

    public List<PermissionResource> fetchResourceListBySubjectList(List<Long> subjectList){
        return permissionResourceMapper.fetchResourceListBySubjectList(subjectList);
    }

    @Transactional
    public Integer permissionRoleResource(List<PermissionResource> resourceList, Long roleId){
        Integer count = permissionResourceMapper.deleteByRoleId(roleId);
        for (PermissionResource resource : resourceList){
            count += super.insertSelective(resource);
        }
        return count;
    }

}
