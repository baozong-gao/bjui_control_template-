package com.shenxin.core.control.service;

import com.shenxin.core.control.entity.SysFunctionDAO;
import com.shenxin.core.control.entity.SysRoleDAO;

import java.util.List;

/**
 * Created by APPLE on 16/1/13.
 */
public interface UserAuthorService {

    List<SysFunctionDAO> getAuthorByUserId(String userId);

    List<SysRoleDAO> getRoleByUserId(String userId);

}
