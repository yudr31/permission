package com.spring.boot.permission.mapper;


import com.spring.boot.common.bean.BaseBeanMapper;
import com.spring.boot.feign.pojo.permission.PermissionMenu;
import com.spring.boot.feign.pojo.permission.vo.PermissionMenuVO;

import java.util.List;

/**
 * @author yuderen
 * @version 2018/9/8 17:37
 */
public interface PermissionMenuMapper extends BaseBeanMapper<PermissionMenu> {

    List<PermissionMenuVO> fetchPermissionMenuList(PermissionMenu permissionMenu);

}
