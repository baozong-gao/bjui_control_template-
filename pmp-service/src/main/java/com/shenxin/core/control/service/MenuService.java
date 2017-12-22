package com.shenxin.core.control.service;

import java.util.List;

import com.shenxin.core.control.bo.MenuBO;

/**
 * Created by APPLE on 16/1/12.
 */
public interface MenuService {
    List<MenuBO> getAllEnabledMenuByUserId(String userid);
}
