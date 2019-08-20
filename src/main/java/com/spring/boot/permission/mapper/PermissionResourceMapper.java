package com.spring.boot.permission.mapper;

import com.spring.boot.common.bean.BaseBeanMapper;
import com.spring.boot.feign.pojo.permission.PermissionResource;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author yuderen
 * @version 2018/9/8 17:41
 */
public interface PermissionResourceMapper extends BaseBeanMapper<PermissionResource> {

    Integer deleteByRoleId(Long subjectId);

    List<PermissionResource> fetchResourceListBySubjectList(@Param("subjectList") List<Long> subjectList);

}
