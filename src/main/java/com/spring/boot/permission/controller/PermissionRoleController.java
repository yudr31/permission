package com.spring.boot.permission.controller;

import com.github.pagehelper.PageInfo;
import com.spring.boot.common.annotation.validation.ValidationExecutor;
import com.spring.boot.common.bean.BaseController;
import com.spring.boot.common.bean.ResponseData;
import com.spring.boot.feign.pojo.permission.PermissionRole;
import com.spring.boot.permission.service.PermissionRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author yuderen
 * @version 2018-9-14 16:50:20
 */
@Api(tags = "权限角色模块", description = "权限角色相关接口")
@RestController
@RequestMapping("/permissionRole")
public class PermissionRoleController extends BaseController {

    @Autowired
    private PermissionRoleService permissionRoleService;

    @ApiOperation(value = "查询权限角色分页信息", httpMethod = "POST", notes = "上送参数：详见 PermissionRole 对象，返回参数说明：见 PermissionRole 对象说明")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页码", example = "1", defaultValue = "1",paramType = "query", dataType = "int", dataTypeClass = Integer.class),
            @ApiImplicitParam(name = "pageSize", value = "页容量", example = "10", defaultValue = "10", paramType = "query", dataType = "int", dataTypeClass = Integer.class)
    })
    @RequestMapping("/permissionRolePage")
    public ResponseData<PageInfo<PermissionRole>> permissionRolePageInfo(@RequestBody PermissionRole permissionRole){
        return successResponse(permissionRoleService.fetchRecordPageInfo(permissionRole));
    }

    @ApiOperation(value = "查询权限角色列表信息", httpMethod = "POST", notes = "上送参数：详见 PermissionRole 对象，返回参数说明：见 PermissionRole 对象说明")
    @RequestMapping("/permissionRoleList")
    public ResponseData<List<PermissionRole>> permissionRoleList(@RequestBody PermissionRole permissionRole){
        return successResponse(permissionRoleService.fetchRecordList(permissionRole));
    }

    @ApiOperation(value = "添加权限角色信息", httpMethod = "POST", notes = "上送参数：详见 PermissionRole 对象，返回参数说明：无")
    @RequestMapping("/addPermissionRole")
    public ResponseData<Integer> addPermissionRole(@RequestBody PermissionRole permissionRole){
        Integer result = permissionRoleService.insertSelective(permissionRole);
        return result > 0 ? successResponse(result) : errorResponse("添加角色信息失败！");
    }

    @ApiOperation(value = "更新权限角色信息", httpMethod = "POST", notes = "上送参数：详见 PermissionRole 对象，返回参数说明：无")
    @RequestMapping("/updatePermissionRole")
    public ResponseData<Integer> updatePermissionRole(@RequestBody PermissionRole permissionRole){
        String[] fields = {"gid"};
        ValidationExecutor.notNullValidate(fields, permissionRole);
        Integer result = permissionRoleService.updateSelectiveByKey(permissionRole);
        return result > 0 ? successResponse(result) : errorResponse("修改角色信息失败");
    }

    @ApiOperation(value = "系统权限角色详情", httpMethod = "POST", notes = "上送参数：gid ，返回参数说明：PermissionRole 对象")
    @RequestMapping("/permissionRoleDetail")
    public ResponseData<PermissionRole> permissionRoleDetail(@RequestParam("gid") Long gid){
        if (null == gid){
            return errorResponse("gid不能为空");
        }
        return successResponse(permissionRoleService.fetchRecordByGid(gid)) ;
    }

    @ApiOperation(value = "删除权限用户信息", httpMethod = "POST", notes = "上送参数：gid ，返回参数说明：无")
    @RequestMapping("/removePermissionRole")
    public ResponseData<Integer> removePermissionRole(@RequestParam("gid") Long gid){
        if (null == gid){
            return errorResponse("gid不能为空");
        }
        Integer result = permissionRoleService.removeRecord(gid);
        return result > 0 ? successResponse(result) : errorResponse("删除角色信息失败");
    }

}
