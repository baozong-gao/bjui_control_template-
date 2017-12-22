package com.shenxin.core.control.mapper;

import com.shenxin.core.control.entity.SysFunctionDAO;
import com.shenxin.core.control.entity.SysRoleDAO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserAuthorMapper {

    List<SysFunctionDAO> getAuthorByUserId(@Param("userId") String userId);

    List<SysRoleDAO> getRoleByUserId(@Param("userId") String userId);
}
