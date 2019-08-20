package com.spring.boot.permission.controller;

import com.github.pagehelper.PageInfo;
import com.spring.boot.common.annotation.validation.ValidationExecutor;
import com.spring.boot.common.bean.BaseController;
import com.spring.boot.common.bean.ResponseData;
import com.spring.boot.feign.pojo.permission.PermissionResource;
import com.spring.boot.permission.service.PermissionResourceService;
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
 * @version 2018-9-17 15:53:55
 */
@Api(tags = "权限资源模块", description = "权限资源相关接口")
@RestController
@RequestMapping("/permissionResource")
public class PermissionResourceController extends BaseController {

    @Autowired
    private PermissionResourceService permissionResourceService;

    @ApiOperation(value = "查询权限资源分页信息", httpMethod = "POST", notes = "上送参数：详见 PermissionResource 对象，返回参数说明：见 PermissionResource 对象说明")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页码", example = "1", defaultValue = "1",paramType = "query", dataType = "int", dataTypeClass = Integer.class),
            @ApiImplicitParam(name = "pageSize", value = "页容量", example = "10", defaultValue = "10", paramType = "query", dataType = "int", dataTypeClass = Integer.class)
    })
    @RequestMapping("/permissionResourcePage")
    public ResponseData<PageInfo<PermissionResource>> permissionResourcePageInfo(@RequestBody PermissionResource resource){
        return successResponse(permissionResourceService.fetchRecordPageInfo(resource));
    }

    @ApiOperation(value = "查询权限资源列表信息", httpMethod = "POST", notes = "上送参数：详见 PermissionResource 对象，返回参数说明：见 PermissionResource 对象说明")
    @RequestMapping("/permissionResourceList")
    public ResponseData<List<PermissionResource>> permissionResourceList(@RequestBody PermissionResource resource){
        return successResponse(permissionResourceService.fetchRecordList(resource));
    }

    @ApiOperation(value = "添加权限资源信息", httpMethod = "POST", notes = "上送参数：详见 PermissionResource 对象，返回参数说明：无")
    @RequestMapping("/addPermissionResource")
    public ResponseData<Integer> addPermissionResource(@RequestBody PermissionResource resource){
        Integer result = permissionResourceService.insertSelective(resource);
        return result > 0 ? successResponse(result) : errorResponse("添加咨询信息失败！");
    }

    @ApiOperation(value = "更新权限资源信息", httpMethod = "POST", notes = "上送参数：详见 PermissionResource 对象，返回参数说明：无")
    @RequestMapping("/updatePermissionResource")
    public ResponseData<Integer> updatePermissionResource(@RequestBody PermissionResource resource){
        String[] fields = {"gid"};
        ValidationExecutor.notNullValidate(fields, resource);
        Integer result = permissionResourceService.updateSelectiveByKey(resource);
        return result > 0 ? successResponse(result) : errorResponse("修改咨询信息失败");
    }

    @ApiOperation(value = "系统权限资源详情", httpMethod = "POST", notes = "上送参数：gid ，返回参数说明：PermissionResource 对象")
    @RequestMapping("/permissionResourceDetail")
    public ResponseData<PermissionResource> permissionResourceDetail(@RequestParam("gid") Long gid){
        if (null == gid){
            return errorResponse("gid不能为空");
        }
        return successResponse(permissionResourceService.fetchRecordByGid(gid)) ;
    }

    @ApiOperation(value = "删除权限资源信息", httpMethod = "POST", notes = "上送参数：gid ，返回参数说明：无")
    @RequestMapping("/removePermissionResource")
    public ResponseData<Integer> removePermissionResource(@RequestParam("gid") Long gid){
        if (null == gid){
            return errorResponse("gid不能为空");
        }
        Integer result = permissionResourceService.removeRecord(gid);
        return result > 0 ? successResponse(result) : errorResponse("删除咨询信息失败");
    }

}
