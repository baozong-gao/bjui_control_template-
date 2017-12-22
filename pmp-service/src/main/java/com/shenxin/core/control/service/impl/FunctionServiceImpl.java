package com.shenxin.core.control.service.impl;

import com.shenxin.core.control.bo.FunctionBO;
import com.shenxin.core.control.entity.SysFunctionDAO;
import com.shenxin.core.control.entity.SysFunctionDAOExample;
import com.shenxin.core.control.mapper.SeqMapper;
import com.shenxin.core.control.mapper.SysFunctionDAOMapper;
import com.shenxin.core.control.service.IFunctionService;
import com.shenxin.core.control.sysexception.PersistentException;
import com.shenxin.core.control.util.BeanUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: gaobaozong
 * @Description: 权限查询服务
 * @Date: Created in 2017/12/15 - 11:05
 * @Version: V1.0
 */
@Slf4j
@Service("functionService")
public class FunctionServiceImpl implements IFunctionService<SysFunctionDAO,FunctionBO>{

    @Autowired
    SysFunctionDAOMapper mapper;

    @Autowired
    SeqMapper seqMapper;


    private SysFunctionDAOExample getSearchFiled(FunctionBO bo){
        SysFunctionDAOExample example =  new SysFunctionDAOExample();
        SysFunctionDAOExample.Criteria criteria =  example.createCriteria();
        if(bo == null){
            return example;
        }
        if(BeanUtils.filedNotNull(bo.getId())){
            criteria.andIdEqualTo(new BigDecimal(bo.getId()));
        }
        if(BeanUtils.filedNotNull(bo.getName())){
            criteria.andNameEqualTo(bo.getName());
        }
        if(BeanUtils.filedNotNull(bo.getCode())){
            criteria.andCodeEqualTo(bo.getCode());
        }
        if(BeanUtils.filedNotNull(bo.getGrade())){
            criteria.andGradeEqualTo(bo.getGrade());
        }
        if(BeanUtils.filedNotNull(bo.getParentId())){
            criteria.andParentIdEqualTo(new BigDecimal(bo.getParentId()));
        }
        if(BeanUtils.filedNotNull(bo.getStatus())){
            criteria.andStatusEqualTo(bo.getStatus());
        }
        return example;
    }

    @Override
    public int count(FunctionBO form) {
        SysFunctionDAOExample ex = getSearchFiled(form);
        return  mapper.countByExample(ex);
    }

    @Override
    public List<SysFunctionDAO> search(FunctionBO form) {
        SysFunctionDAOExample ex = getSearchFiled(form);
        List<SysFunctionDAO> result = mapper.selectByExample(ex);
        if(result == null){
            result = new ArrayList<>();
        }
        return result;
    }

    @Override
    public int delete(String id) throws PersistentException{
        if(id == null){
            throw new PersistentException("删除权限失败：参数为空");
        }
        return  mapper.deleteByPrimaryKey(new BigDecimal(id));
    }

    @Override
    public int insert(SysFunctionDAO function)throws PersistentException {
        if(function == null){
            throw new PersistentException("增加权限失败：参数为空");
        }
        int id = seqMapper.getFunctionSeq();
        function.setId(new BigDecimal(id));
        return mapper.insertSelective(function);
    }

    @Override
    public SysFunctionDAO searchById(String id) {
        if(StringUtils.isBlank(id)){
            return null;
        }
        return mapper.selectByPrimaryKey(new BigDecimal(id));
    }

    @Override
    public int update(SysFunctionDAO function)throws PersistentException {
        if(function == null || function.getId() == null){
            throw new PersistentException("更新权限失败：id为空");
        }
        return mapper.updateByPrimaryKeySelective(function);
    }
}
