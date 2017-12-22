package com.shenxin.core.control.service.impl;

import com.shenxin.core.control.bo.UserBO;
import com.shenxin.core.control.entity.SysUserDAO;
import com.shenxin.core.control.entity.SysUserDAOExample;
import com.shenxin.core.control.entity.TblBTSSysUsrRoleDO;
import com.shenxin.core.control.entity.TblBTSSysUsrRoleDOExample;
import com.shenxin.core.control.mapper.SeqMapper;
import com.shenxin.core.control.mapper.SysUserDAOMapper;
import com.shenxin.core.control.mapper.TblBTSSysUsrRoleDOMapper;
import com.shenxin.core.control.service.IUserService;
import com.shenxin.core.control.sysexception.PersistentException;
import com.shenxin.core.control.util.BeanUtils;
import com.shenxin.core.control.util.EncryptUtils;
import com.shenxin.core.control.vo.UserVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Author: gaobaozong
 * @Description: 用户信息服务
 * @Date: Created in 2017/12/21 - 14:07
 * @Version: V1.0
 */
@Service("userService")
public class UserServiceImpl implements IUserService<SysUserDAO, UserBO> {

    @Autowired
    SeqMapper seqMapper;

    @Autowired
    SysUserDAOMapper mapper;

    @Autowired
    TblBTSSysUsrRoleDOMapper usrRoleDOMapper;

    private SysUserDAOExample getSearchFiled(UserBO bo) {
        SysUserDAOExample example = new SysUserDAOExample();
        SysUserDAOExample.Criteria criteria = example.createCriteria();
        if (bo == null) {
            return example;
        }
        if (BeanUtils.filedNotNull(bo.getId())) {
            criteria.andIdEqualTo(bo.getId());
        }
        if (BeanUtils.filedNotNull(bo.getName())) {
            criteria.andNameEqualTo(bo.getName());
        }
        if (BeanUtils.filedNotNull(bo.getEmail())) {
            criteria.andEmailEqualTo(bo.getEmail());
        }
        if (BeanUtils.filedNotNull(bo.getPwd())) {
            criteria.andPwdEqualTo(bo.getPwd());
        }
        if (BeanUtils.filedNotNull(bo.getRemark())) {
            criteria.andRemarkEqualTo(bo.getRemark());
        }
        if (BeanUtils.filedNotNull(bo.getDisableTag())) {
            criteria.andDisableTagEqualTo(bo.getDisableTag());
        }
        if (BeanUtils.filedNotNull(bo.getType())) {
            criteria.andTypeEqualTo(bo.getType());
        }
        return example;
    }

    @Override
    public int count(UserBO form) {
        SysUserDAOExample example = getSearchFiled(form);
        return mapper.countByExample(example);
    }

    @Override
    public List<SysUserDAO> search(UserBO form) {
        SysUserDAOExample example = getSearchFiled(form);
        return mapper.selectByExample(example);
    }

    @Override
    public int delete(String id) throws PersistentException {
        if (id == null) {
            throw new PersistentException("删除失败：参数为空");
        }
        return mapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(SysUserDAO function) throws PersistentException {
        if (function == null) {
            throw new PersistentException("增加失败：参数为空");
        }
        int id = seqMapper.getUsrSeq();
        function.setId(id + "");
        function.setPwd(EncryptUtils.encryptPwd(function.getPwd()));
        return mapper.insertSelective(function);
    }

    @Override
    public SysUserDAO searchById(String id) {
        if (StringUtils.isBlank(id)) {
            return null;
        }
        return mapper.selectByPrimaryKey(id);
    }

    @Override
    public int update(SysUserDAO function) throws PersistentException {
        if (function == null || function.getId() == null) {
            throw new PersistentException("更新失败：id为空");
        }
        if (StringUtils.isNotBlank(function.getPwd())) {
            function.setPwd(EncryptUtils.encryptPwd(function.getPwd()));
        }
        return mapper.updateByPrimaryKeySelective(function);
    }

    @Override
    public List getRoleByUserId(String id) {
        TblBTSSysUsrRoleDOExample example = new TblBTSSysUsrRoleDOExample();
        example.createCriteria().andUsrIdEqualTo(id);
        List<TblBTSSysUsrRoleDO> ur = usrRoleDOMapper.selectByExample(example);
        Set<String> roleIDs = ur.stream().map(rf -> rf.getRoleId()).collect(Collectors.toSet());

        List<SysUserDAO> allFunction = mapper.selectByExample(null);
        List<UserVO> result = BeanUtils.copyList(allFunction, UserVO.class);
        result.stream().forEach(func -> func.setAuth(roleIDs.contains(func.getId())));

        return result;
    }

    @Override
    public void upRoleOnUser(String uid, String rid, boolean isDel) {
        if (isDel) {
            TblBTSSysUsrRoleDOExample example = new TblBTSSysUsrRoleDOExample();
            example.createCriteria().andUsrIdEqualTo(uid).andRoleIdEqualTo(rid);
            usrRoleDOMapper.deleteByExample(example);
        } else {
            TblBTSSysUsrRoleDO record = new TblBTSSysUsrRoleDO();
            record.setRoleId(rid);
            record.setUsrId(uid);
            usrRoleDOMapper.insert(record);
        }
    }
}
