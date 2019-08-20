package com.spring.boot.permission.controller;

import com.github.pagehelper.PageInfo;
import com.spring.boot.common.annotation.validation.ValidationExecutor;
import com.spring.boot.common.bean.BaseController;
import com.spring.boot.common.bean.ResponseData;
import com.spring.boot.feign.pojo.permission.PermissionUser;
import com.spring.boot.permission.service.PermissionUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * @author yuderen
 * @version 2018-9-17 14:10:02
 */
@Slf4j
@Api(tags = "权限用户模块", description = "权限用户相关接口")
@RestController
@RequestMapping("/permissionUser")
public class PermissionUserController extends BaseController {

    @Autowired
    private PermissionUserService permissionUserService;

    @ApiOperation(value = "查询权限用户分页信息", httpMethod = "POST", notes = "上送参数：详见 PermissionUser 对象，返回参数说明：见 PermissionUser 对象说明")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页码", example = "1", defaultValue = "1",paramType = "query", dataType = "int", dataTypeClass = Integer.class),
            @ApiImplicitParam(name = "pageSize", value = "页容量", example = "10", defaultValue = "10", paramType = "query", dataType = "int", dataTypeClass = Integer.class)
    })
    @RequestMapping("/permissionUserPage")
    public ResponseData<PageInfo<PermissionUser>> permissionUserPage(@RequestBody PermissionUser permissionUser){
        log.info("test info");
        log.debug("test debug");
        log.error("test error");
        return successResponse(permissionUserService.fetchRecordPageInfo(permissionUser));
    }

    @ApiOperation(value = "查询权限用户列表信息", httpMethod = "POST", notes = "上送参数：详见 PermissionUser 对象，返回参数说明：见 PermissionUser 对象说明")
    @RequestMapping("/permissionUserList")
    public ResponseData<List<PermissionUser>> permissionUserList(@RequestBody PermissionUser permissionUser){
        return successResponse(permissionUserService.fetchRecordList(permissionUser));
    }

    @ApiOperation(value = "添加权限用户信息", httpMethod = "POST", notes = "上送参数：详见 PermissionUser 对象，返回参数说明：无")
    @RequestMapping("/addPermissionUser")
    public ResponseData<Integer> addPermissionUser(@RequestBody PermissionUser permissionUser){
        Integer result = permissionUserService.insertSelective(permissionUser);
        return result > 0 ? successResponse(result) : errorResponse("添加用户信息失败！");
    }

    @ApiOperation(value = "更新权限用户信息", httpMethod = "POST", notes = "上送参数：详见 PermissionUser 对象，返回参数说明：无")
    @RequestMapping("/updatePermissionUser")
    public ResponseData<Integer> updatePermissionUser(@RequestBody PermissionUser permissionUser){
        String[] fields = {"gid"};
        ValidationExecutor.notNullValidate(fields, permissionUser);
        Integer result = permissionUserService.updateSelectiveByKey(permissionUser);
        return result > 0 ? successResponse(result) : errorResponse("更新用户信息失败！");
    }

    @ApiOperation(value = "系统权限用户详情", httpMethod = "POST", notes = "上送参数：gid ，返回参数说明：PermissionUser 对象")
    @RequestMapping("/permissionUserDetail")
    public ResponseData<PermissionUser> permissionUserDetail(@RequestParam("gid") Long gid){
        if (null == gid){
            return errorResponse("gid不能为空");
        }
        return successResponse(permissionUserService.fetchRecordByGid(gid)) ;
    }

    @ApiOperation(value = "删除权限角色信息", httpMethod = "POST", notes = "上送参数：gid ，返回参数说明：无")
    @RequestMapping("/removePermissionUser")
    public ResponseData<Integer> removePermissionUser(@RequestParam("gid") Long gid){
        if (null == gid){
            return errorResponse("gid不能为空");
        }
        Integer result = permissionUserService.removeRecord(gid);
        return result > 0 ? successResponse(result) : errorResponse("删除用户信息失败！");
    }

}
