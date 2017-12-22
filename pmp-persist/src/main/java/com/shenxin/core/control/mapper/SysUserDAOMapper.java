package com.shenxin.core.control.mapper;

import com.shenxin.core.control.entity.SysUserDAO;
import com.shenxin.core.control.entity.SysUserDAOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SysUserDAOMapper {
    int countByExample(SysUserDAOExample example);

    int deleteByExample(SysUserDAOExample example);

    int deleteByPrimaryKey(String id);

    int insert(SysUserDAO record);

    int insertSelective(SysUserDAO record);

    List<SysUserDAO> selectByExample(SysUserDAOExample example);

    SysUserDAO selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") SysUserDAO record, @Param("example") SysUserDAOExample example);

    int updateByExample(@Param("record") SysUserDAO record, @Param("example") SysUserDAOExample example);

    int updateByPrimaryKeySelective(SysUserDAO record);

    int updateByPrimaryKey(SysUserDAO record);
}