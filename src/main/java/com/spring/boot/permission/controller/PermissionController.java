package com.spring.boot.permission.controller;

import com.spring.boot.common.bean.BaseController;
import com.spring.boot.common.bean.ResponseData;
import com.spring.boot.feign.pojo.permission.PermissionMenu;
import com.spring.boot.feign.pojo.permission.PermissionResource;
import com.spring.boot.feign.pojo.permission.PermissionRole;
import com.spring.boot.feign.pojo.permission.vo.PermissionMenuVO;
import com.spring.boot.permission.service.PermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author yuderen
 * @version 2018/9/8 17:54
 */
@Api(tags = "权限整合模块", description = "权限整合相关接口")
@RestController
@RequestMapping("/permission")
public class PermissionController extends BaseController {

    @Autowired
    private PermissionService permissionService;

    @ApiOperation(value = "添加权限菜单", httpMethod = "POST", notes = "上送参数：见 PermissionMenu 对象")
    @RequestMapping("/addPermissionMenu")
    public ResponseData<Integer> addPermissionMenu(@RequestBody PermissionMenu permissionMenu){
        return successResponse(permissionService.addPermissionMenu(permissionMenu));
    }

    @ApiOperation(value = "添加权限角色", httpMethod = "POST", notes = "上送参数：见 PermissionRole 对象")
    @RequestMapping("/addPermissionRole")
    public ResponseData<Integer> addPermissionRole(@RequestBody PermissionRole permissionRole){
        return successResponse(permissionService.addPermissionRole(permissionRole));
    }

    @ApiOperation(value = "添加权限资源", httpMethod = "POST", notes = "上送参数：见 PermissionRole 对象")
    @RequestMapping("/addPermissionResource")
    public ResponseData<Integer> addPermissionResource(@RequestBody PermissionResource permissionResource){
        return successResponse(permissionService.addPermissionResource(permissionResource));
    }

    @ApiOperation(value = "获取用户权限资源列表", httpMethod = "POST", notes = "上送参数：无")
    @RequestMapping("/fetchPermissionMenuList")
    public ResponseData<List<PermissionMenuVO>> fetchPermissionMenuList(@RequestParam("userId") Long userId){
        return successResponse(permissionService.fetchPermissionMenuList(userId));
    }

//    @ApiOperation(value = "获取所有权限资源", httpMethod = "POST", notes = "上送参数：无")
//    @RequestMapping("/fetchPermissionResource")
//    public ResponseData<List<PermissionResource>> fetchPermissionResource(){
//        return successResponse(permissionService.fetchPermissionResource());
//    }

}
