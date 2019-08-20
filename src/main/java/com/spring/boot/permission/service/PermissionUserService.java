package com.spring.boot.permission.service;

import com.spring.boot.common.bean.BaseBeanService;
import com.spring.boot.feign.pojo.permission.PermissionUser;
import com.spring.boot.permission.mapper.PermissionUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @author yuderen
 * @version 2018-9-17 14:10:02
 */
@Service
public class PermissionUserService extends BaseBeanService<PermissionUserMapper, PermissionUser> {

    @Autowired
    private PermissionUserMapper permissionUserMapper;

}