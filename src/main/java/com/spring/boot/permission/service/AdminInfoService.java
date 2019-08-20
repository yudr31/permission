package com.spring.boot.permission.service;


import com.spring.boot.common.bean.BaseBeanService;
import com.spring.boot.common.exception.BaseException;
import com.spring.boot.common.util.Base64Util;
import com.spring.boot.common.util.MD5Util;
import com.spring.boot.feign.pojo.permission.AdminInfo;
import com.spring.boot.feign.pojo.permission.vo.LoginUserVO;
import com.spring.boot.permission.mapper.AdminInfoMapper;
import org.apache.shiro.codec.Hex;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


/**
 * @author yuderen
 * @version 2018-9-13 16:47:21
 */
@Service
public class AdminInfoService extends BaseBeanService<AdminInfoMapper, AdminInfo> {

    private static final Integer HASH_COUNT = 32;

    @Autowired
    private AdminInfoMapper adminInfoMapper;
    @Autowired
    private PermissionRoleService permissionRoleService;

    @Override
    public Integer insertSelective(AdminInfo adminInfo){
        AdminInfo result = adminInfoMapper.fetchAdminInfoByUserName(adminInfo);
        if (null != result){
            throw new BaseException("0001","用户已存在");
        }
        adminInfo.setSaltRandom(Hex.encodeToString(MD5Util.generateSalt(32)));
        Object password = new Md5Hash(adminInfo.getPassword(), adminInfo.getSaltRandom(),HASH_COUNT);
        adminInfo.setPassword(password.toString());
        return super.insertSelective(adminInfo);
    }

    @Override
    public Integer updateSelectiveByKey(AdminInfo adminInfo){
        Object password = new Md5Hash(adminInfo.getPassword(), adminInfo.getSaltRandom(),HASH_COUNT);
        adminInfo.setPassword(password.toString());
        return super.updateSelectiveByKey(adminInfo);
    }

    public LoginUserVO login(AdminInfo adminInfo){
        AdminInfo result = adminInfoMapper.fetchAdminInfoByUserName(adminInfo);
        if (null == result){
            throw new BaseException("0001","用户不存在");
        }
        Object password = new Md5Hash(adminInfo.getPassword(), result.getSaltRandom(),HASH_COUNT);
        if (!result.getPassword().equals(password.toString())){
            updateFailLoginInfo(result);
            throw new BaseException("0002","密码错误");
        }
        LoginUserVO loginUserVO = new LoginUserVO();
        adminInfo.setGid(result.getGid());
        updateSuccessLoginInfo(result);
        loginUserVO.setUserId(result.getGid());
        loginUserVO.setToken(getAuthToken(result));
        loginUserVO.setIsAdminRole(permissionRoleService.isAdminRole(result.getGid()));
        loginUserVO.setIsLogined(true);
        return loginUserVO;
    }

    private void updateSuccessLoginInfo(AdminInfo adminInfo){
        adminInfo.setLoginTime(LocalDateTime.now());
        adminInfo.setLoginCount(adminInfo.getLoginCount() + 1);
        adminInfo.setLoginFlag(1);
        adminInfo.setLockStatus(0);
        adminInfo.setFailCount(0);
        super.updateSelectiveByKey(adminInfo);
    }

    private void updateFailLoginInfo(AdminInfo adminInfo){
        adminInfo.setFailCount(adminInfo.getFailCount() + 1);
        if (adminInfo.getFailCount() > 5){
            adminInfo.setLockStatus(1);
        }
        super.updateSelectiveByKey(adminInfo);
    }

    private String getAuthToken(AdminInfo adminInfo){
        return Base64Util.encode(adminInfo.getGid() + "|" + adminInfo.getUserName());
    }

}