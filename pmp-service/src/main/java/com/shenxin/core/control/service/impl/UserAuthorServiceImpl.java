package com.shenxin.core.control.service.impl;

import com.shenxin.core.control.constant.SystemConstant;
import com.shenxin.core.control.entity.*;
import com.shenxin.core.control.mapper.UserAuthorMapper;
import com.shenxin.core.control.service.IFunctionService;
import com.shenxin.core.control.service.IRoleService;
import com.shenxin.core.control.service.IUserService;
import com.shenxin.core.control.service.UserAuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userAuthorService")
public class UserAuthorServiceImpl implements UserAuthorService {

    @Autowired
    UserAuthorMapper userAuthorMapper;

    @Autowired
    IUserService userService;

    @Autowired
    IFunctionService functionService;

    @Autowired
    IRoleService roleService;

    public List<SysFunctionDAO> getAuthorByUserId(String userId) {
        SysUserDAO user = (SysUserDAO)userService.searchById(userId);
        if (SystemConstant.ROOT.equals(user.getName())) {
            return functionService.search(null);
        } else {
            return userAuthorMapper.getAuthorByUserId(userId);
        }
    }

    @Override
    public List<SysRoleDAO> getRoleByUserId(String userId) {
        SysUserDAO user = (SysUserDAO)userService.searchById(userId);
        if (SystemConstant.ROOT.equals(user.getName())) {
            return roleService.search(null);
        } else {
            return userAuthorMapper.getRoleByUserId(userId);
        }
    }
}
