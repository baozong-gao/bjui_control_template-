package com.shenxin.core.control.service;

import com.shenxin.core.control.sysexception.PersistentException;

import java.util.List;

/**
 * @Author: gaobaozong
 * @Description: 权限服务接口
 * @Date: Created in 2017/12/15 - 11:00
 * @Version: V1.0
 */
public interface IFunctionService<D,B> {

    int count(B form);

    List<D> search(B form);

    int delete(String id) throws PersistentException;

    int insert(D function) throws PersistentException;

    D searchById(String id);

    int update(D function) throws PersistentException;
}
