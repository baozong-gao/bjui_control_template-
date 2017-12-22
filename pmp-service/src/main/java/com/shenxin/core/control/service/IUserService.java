package com.shenxin.core.control.service;

import com.shenxin.core.control.sysexception.PersistentException;

import java.util.List;

/**
 * @Author: gaobaozong
 * @Description: 用户服务接口
 * @Date: Created in 2017/12/21 - 14:06
 * @Version: V1.0
 */
public interface IUserService<D,B> {
    int count(B form);

    List<D> search(B form);

    int delete(String id) throws PersistentException;

    int insert(D function) throws PersistentException;

    D searchById(String id);

    int update(D function) throws PersistentException;

    List getRoleByUserId(String id);

    void upRoleOnUser(String uid,String rid,boolean isDel);
}
