package com.spring.boot.permission.controller;

import com.github.pagehelper.PageInfo;
import com.spring.boot.common.annotation.validation.ValidationExecutor;
import com.spring.boot.common.bean.BaseController;
import com.spring.boot.common.bean.ResponseData;
import com.spring.boot.feign.pojo.permission.AdminInfo;
import com.spring.boot.feign.pojo.permission.PermissionMenu;
import com.spring.boot.feign.pojo.permission.vo.LoginUserVO;
import com.spring.boot.permission.service.AdminInfoService;
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
 * @version 2018-9-13 16:48:35
 */
@Api(tags = "登录用户模块", description = "登录用户相关接口")
@RestController
@RequestMapping("/adminInfo")
public class AdminInfoController extends BaseController {

    @Autowired
    private AdminInfoService adminInfoService;

    @ApiOperation(value = "查询系统用户分页信息", httpMethod = "POST", notes = "上送参数：详见 AdminInfo 对象，返回参数说明：见 AdminInfo 对象说明")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页码", example = "1", defaultValue = "1",paramType = "query", dataType = "int", dataTypeClass = Integer.class),
            @ApiImplicitParam(name = "pageSize", value = "页容量", example = "10", defaultValue = "10", paramType = "query", dataType = "int", dataTypeClass = Integer.class)
    })
    @RequestMapping("/adminInfoPage")
    public ResponseData<PageInfo<PermissionMenu>> adminInfoPageInfo(@RequestBody AdminInfo adminInfo){
        return successResponse(adminInfoService.fetchRecordPageInfo(adminInfo));
    }

    @ApiOperation(value = "查询系统用户列表信息", httpMethod = "POST", notes = "上送参数：详见 AdminInfo 对象，返回参数说明：见 AdminInfo 对象说明")
    @RequestMapping("/adminInfoList")
    public ResponseData<List<PermissionMenu>> adminInfoList(@RequestBody AdminInfo adminInfo){
        return successResponse(adminInfoService.fetchRecordList(adminInfo));
    }

    @ApiOperation(value = "添加系统用户信息", httpMethod = "POST", notes = "上送参数：详见 AdminInfo 对象，返回参数说明：无")
    @RequestMapping("/addAdminInfo")
    public ResponseData<Integer> addAdminInfo(@RequestBody AdminInfo adminInfo){
        Integer result = adminInfoService.insertSelective(adminInfo);
        return result > 0 ? successResponse(result) : errorResponse("添加系统用户信息失败！");
    }

    @ApiOperation(value = "系统用户登录", httpMethod = "POST", notes = "上送参数：详见 AdminInfo 对象，返回参数说明：token")
    @RequestMapping("/login")
    public ResponseData<LoginUserVO> login(@RequestBody AdminInfo adminInfo){
        LoginUserVO userVO = adminInfoService.login(adminInfo);
        return null != userVO ? successResponse(userVO) : errorResponse("登陆失败！");
    }

    @ApiOperation(value = "更新系统用户信息", httpMethod = "POST", notes = "上送参数：详见 AdminInfo 对象，返回参数说明：无")
    @RequestMapping("/updateAdminInfo")
    public ResponseData<Integer> updateAdminInfo(@RequestBody AdminInfo adminInfo){
        String[] fields = {"gid"};
        ValidationExecutor.notNullValidate(fields, adminInfo);
        Integer result = adminInfoService.updateSelectiveByKey(adminInfo);
        return result > 0 ? successResponse(result) : errorResponse("修改系统用户信息失败");
    }

    @ApiOperation(value = "系统用户信息详情", httpMethod = "POST", notes = "上送参数：gid ，返回参数说明：AdminInfo 对象")
    @RequestMapping("/adminInfoDetail")
    public ResponseData<AdminInfo> adminInfoDetail(@RequestParam("gid") Long gid){
        if (null == gid){
            return errorResponse("gid不能为空");
        }
        return successResponse(adminInfoService.fetchRecordByGid(gid)) ;
    }

    @ApiOperation(value = "删除系统用户信息", httpMethod = "POST", notes = "上送参数：gid ，返回参数说明：无")
    @RequestMapping("/removeAdminInfo")
    public ResponseData<Integer> removeAdminInfo(@RequestParam("gid") Long gid){
        if (null == gid){
            return errorResponse("gid不能为空");
        }
        Integer result = adminInfoService.removeRecord(gid);
        return result > 0 ? successResponse(result) : errorResponse("删除系统用户信息失败");
    }


}
