package com.spring.boot.permission.controller;

import com.github.pagehelper.PageInfo;
import com.spring.boot.common.annotation.validation.ValidationExecutor;
import com.spring.boot.common.bean.BaseController;
import com.spring.boot.common.bean.ResponseData;
import com.spring.boot.feign.pojo.permission.PermissionMenu;
import com.spring.boot.permission.service.PermissionMenuService;
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
 * @version 2018-9-14 17:33:25
 */
@Api(tags = "权限菜单模块", description = "权限菜单相关接口")
@RestController
@RequestMapping("/permissionMenu")
public class PermissionMenuController extends BaseController {

    @Autowired
    private PermissionMenuService permissionMenuService;

    @ApiOperation(value = "查询权限菜单分页信息", httpMethod = "POST", notes = "上送参数：详见 PermissionMenu 对象，返回参数说明：见 PermissionMenu 对象说明")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页码", example = "1", defaultValue = "1",paramType = "query", dataType = "int", dataTypeClass = Integer.class),
            @ApiImplicitParam(name = "pageSize", value = "页容量", example = "10", defaultValue = "10", paramType = "query", dataType = "int", dataTypeClass = Integer.class)
    })
    @RequestMapping("/permissionMenuPage")
    public ResponseData<PageInfo<PermissionMenu>> permissionUserPageInfo(@RequestBody PermissionMenu permissionMenu){
        return successResponse(permissionMenuService.fetchRecordPageInfo(permissionMenu));
    }

    @ApiOperation(value = "查询权限菜单列表信息", httpMethod = "POST", notes = "上送参数：详见 PermissionMenu 对象，返回参数说明：见 PermissionMenu 对象说明")
    @RequestMapping("/permissionMenuList")
    public ResponseData<List<PermissionMenu>> fetchPermissionUserList(@RequestBody PermissionMenu permissionMenu){
        return successResponse(permissionMenuService.fetchRecordList(permissionMenu));
    }

    @ApiOperation(value = "添加权限菜单信息", httpMethod = "POST", notes = "上送参数：详见 PermissionMenu 对象，返回参数说明：无")
    @RequestMapping("/addPermissionMenu")
    public ResponseData<Integer> addPermissionMenu(@RequestBody PermissionMenu permissionMenu){
        Integer result = permissionMenuService.insertSelective(permissionMenu);
        return result > 0 ? successResponse(result) : errorResponse("添加菜单信息失败！");
    }

    @ApiOperation(value = "更新权限菜单信息", httpMethod = "POST", notes = "上送参数：详见 PermissionMenu 对象，返回参数说明：无")
    @RequestMapping("/updatePermissionMenu")
    public ResponseData<Integer> updatePermissionMenu(@RequestBody PermissionMenu permissionMenu){
        String[] fields = {"gid"};
        ValidationExecutor.notNullValidate(fields, permissionMenu);
        Integer result = permissionMenuService.updateSelectiveByKey(permissionMenu);
        return result > 0 ? successResponse(result) : errorResponse("修改菜单信息失败");
    }

    @ApiOperation(value = "系统权限菜单详情", httpMethod = "POST", notes = "上送参数：gid ，返回参数说明：PermissionMenu 对象")
    @RequestMapping("/permissionMenuDetail")
    public ResponseData<PermissionMenu> permissionMenuDetail(@RequestParam("gid") Long gid){
        if (null == gid){
            return errorResponse("gid不能为空");
        }
        return successResponse(permissionMenuService.fetchRecordByGid(gid)) ;
    }

    @ApiOperation(value = "删除权限菜单信息", httpMethod = "POST", notes = "上送参数：gid ，返回参数说明：无")
    @RequestMapping("/removePermissionMenu")
    public ResponseData<Integer> removePermissionMenu(@RequestParam("gid") Long gid){
        if (null == gid){
            return errorResponse("gid不能为空");
        }
        Integer result = permissionMenuService.removeRecord(gid);
        return result > 0 ? successResponse(result) : errorResponse("删除菜单信息失败");
    }

}
