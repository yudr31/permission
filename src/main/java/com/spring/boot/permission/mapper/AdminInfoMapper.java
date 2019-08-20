package com.spring.boot.permission.mapper;


import com.spring.boot.common.bean.BaseBeanMapper;
import com.spring.boot.feign.pojo.permission.AdminInfo;

/**
 * @author yuderen
 * @version 2018/9/8 17:27
 */
public interface AdminInfoMapper extends BaseBeanMapper<AdminInfo> {

    AdminInfo fetchAdminInfoByUserName(AdminInfo adminInfo);

}
