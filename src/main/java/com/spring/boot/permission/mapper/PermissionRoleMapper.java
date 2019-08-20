package com.spring.boot.permission.mapper;

import com.spring.boot.common.bean.BaseBeanMapper;
import com.spring.boot.feign.pojo.permission.PermissionRole;

import java.util.List;

/**
 * @author yuderen
 * @version 2018/9/8 17:34
 */
public interface PermissionRoleMapper extends BaseBeanMapper<PermissionRole> {

    List<PermissionRole> fetchPermissionRoleByUserId(Long userId);

}
