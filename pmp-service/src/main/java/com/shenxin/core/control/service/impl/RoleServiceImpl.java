package com.shenxin.core.control.service.impl;

import com.shenxin.core.control.bo.SysRoleBO;
import com.shenxin.core.control.entity.*;
import com.shenxin.core.control.mapper.SeqMapper;
import com.shenxin.core.control.mapper.SysFunctionDAOMapper;
import com.shenxin.core.control.mapper.SysRoleDAOMapper;
import com.shenxin.core.control.mapper.TblBTSSysRoleFuncDOMapper;
import com.shenxin.core.control.service.IRoleService;
import com.shenxin.core.control.sysexception.PersistentException;
import com.shenxin.core.control.util.BeanUtils;
import com.shenxin.core.control.vo.FunctionVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: gaobaozong
 * @Description: 角色服务
 * @Date: Created in 2017/12/19 - 10:50
 * @Version: V1.0
 */
@Slf4j
@Service("roleService")
public class RoleServiceImpl implements IRoleService<SysRoleDAO, SysRoleBO> {

    @Autowired
    SysRoleDAOMapper mapper;

    @Autowired
    TblBTSSysRoleFuncDOMapper roleFuncMapper;

    @Autowired
    SysFunctionDAOMapper funcMapper;

    @Autowired
    SeqMapper seqMapper;

    private SysRoleDAOExample getSearchFiled(SysRoleBO bo) {
        SysRoleDAOExample example = new SysRoleDAOExample();
        SysRoleDAOExample.Criteria criteria = example.createCriteria();
        if (bo == null) {
            return example;
        }
        if (BeanUtils.filedNotNull(bo.getId())) {
            criteria.andIdEqualTo(bo.getId());
        }
        if (BeanUtils.filedNotNull(bo.getRemark())) {
            criteria.andRemarkEqualTo(bo.getRemark());
        }
        if (BeanUtils.filedNotNull(bo.getName())) {
            criteria.andNameEqualTo(bo.getName());
        }
        if (BeanUtils.filedNotNull(bo.getDisableTag())) {
            criteria.andDisableTagEqualTo(bo.getDisableTag());
        }
        return example;

    }


    @Override
    public int count(SysRoleBO form) {
        SysRoleDAOExample example =  getSearchFiled(form);
        return mapper.countByExample(example);
    }

    @Override
    public List<SysRoleDAO> search(SysRoleBO form) {
        SysRoleDAOExample example =  getSearchFiled(form);
        List<SysRoleDAO> result = mapper.selectByExample(example);
        if(result == null){
            result = new ArrayList<>();
        }
        return result;
    }

    @Override
    public int delete(String id) throws PersistentException {
        if(id == null){
            throw new PersistentException("删除角色失败：参数为空");
        }
        return  mapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(SysRoleDAO function) throws PersistentException {
        if(function == null){
            throw new PersistentException("增加角色失败：参数为空");
        }
        int id = seqMapper.getRoleSeq();
        function.setId(id+"");
        return mapper.insertSelective(function);
    }

    @Override
    public SysRoleDAO searchById(String id) {
        if(StringUtils.isBlank(id)){
            return null;
        }
        return mapper.selectByPrimaryKey(id);
    }

    @Override
    public int update(SysRoleDAO function) throws PersistentException {
        if(function == null || function.getId() == null){
            throw new PersistentException("更新角色失败：id为空");
        }
        return mapper.updateByPrimaryKeySelective(function);
    }

    @Override
    public List getFunctionByRole(String id) {
        TblBTSSysRoleFuncDOExample example = new TblBTSSysRoleFuncDOExample();
        example.createCriteria().andRoleIdEqualTo(id);
        List<TblBTSSysRoleFuncDO> roleFunc = roleFuncMapper.selectByExample(example);
        Set<String> roleFuncIDs = roleFunc.stream().map(rf -> rf.getFuncId()).collect(Collectors.toSet());

        List<SysFunctionDAO> allFunction = funcMapper.selectByExample(null);
        List<FunctionVO> result = BeanUtils.copyList(allFunction, FunctionVO.class);
        result.stream().forEach(func ->func.setAuth(roleFuncIDs.contains(func.getId().toString())));

        return result;
    }

    @Override
    @Transactional
    public int upFunctionOnRole(String id, List<String> func) {
        TblBTSSysRoleFuncDOExample example = new TblBTSSysRoleFuncDOExample();
        example.createCriteria().andRoleIdEqualTo(id);
        roleFuncMapper.deleteByExample(example);

        Optional.ofNullable(func).ifPresent(funcs ->{
            funcs.stream().forEach(fun ->{
                TblBTSSysRoleFuncDO dao = new TblBTSSysRoleFuncDO();
                dao.setFuncId(fun);
                dao.setRoleId(id);
                roleFuncMapper.insert(dao);
            });
        });
        return 0;
    }
}
