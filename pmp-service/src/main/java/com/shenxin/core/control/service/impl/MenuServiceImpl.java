package com.shenxin.core.control.service.impl;

import com.shenxin.core.control.bo.MenuBO;
import com.shenxin.core.control.entity.SysFunctionDAO;
import com.shenxin.core.control.service.IFunctionService;
import com.shenxin.core.control.service.MenuService;
import com.shenxin.core.control.service.UserAuthorService;
import com.shenxin.core.control.util.BeanUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Created by APPLE on 16/1/12.
 */
@Slf4j
@Service("menuService")
public class MenuServiceImpl implements MenuService {

    @Autowired
    UserAuthorService authorService;

    @Autowired
    IFunctionService functionService;

    public List<MenuBO> getAllEnabledMenuByUserId(String userid) {
        List<MenuBO> menuBOList = new ArrayList<MenuBO>();
        List<SysFunctionDAO> functionDAOList = authorService.getAuthorByUserId(userid);
        Optional.ofNullable(functionDAOList).ifPresent(funcs ->{
            funcs.stream().parallel()
                    .filter( fun -> "A".equalsIgnoreCase(fun.getGrade()))
                    .map(fun -> {
                        try {
                            MenuBO menu = new MenuBO();
                            BeanUtils.copy(fun, menu);
                            menuBOList.add(menu);
                            return menu;
                        }catch (Exception e){}
                        return null;
                    })
                    .filter( menu -> menu != null)
                    .forEach(menu ->{
                        if(menu.getChildMenuBOList() == null) menu.setChildMenuBOList(new ArrayList<>());
                        List<MenuBO> subList = menu.getChildMenuBOList();
                        funcs.stream()
                                .filter(func -> func.getParentId().toString().equals(menu.getFuncId().toString()))
                                .map(subFunc ->{
                                    try {
                                        MenuBO subMenu = new MenuBO();
                                        BeanUtils.copy(subFunc, subMenu);
                                        subList.add(subMenu);
                                        return subMenu;
                                    }catch (Exception e){}
                                    return null;
                                })
                                .filter( subMenu -> subMenu != null)
                                .forEach(subMenu ->{
                                    if(subMenu.getChildMenuBOList() == null) subMenu.setChildMenuBOList(new ArrayList<>());
                                    List<MenuBO> subListc = subMenu.getChildMenuBOList();
                                    funcs.stream().parallel()
                                            .filter(func -> func.getParentId().toString().equals(subMenu.getFuncId().toString()))
                                            .forEach(subFunc ->{
                                                try {
                                                    MenuBO subMenuc = new MenuBO();
                                                    BeanUtils.copy(subFunc, subMenuc);
                                                    subListc.add(subMenuc);
                                                }catch (Exception e){}
                                            });
                                    Collections.sort(subListc);
                                });
                        Collections.sort(subList);
                    });
        });
        Collections.sort(menuBOList);
        return menuBOList;
    }
}
