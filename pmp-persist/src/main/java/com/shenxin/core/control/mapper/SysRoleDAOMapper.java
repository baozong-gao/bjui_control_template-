package com.shenxin.core.control.mapper;

import com.shenxin.core.control.entity.SysRoleDAO;
import com.shenxin.core.control.entity.SysRoleDAOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SysRoleDAOMapper {
    int countByExample(SysRoleDAOExample example);

    int deleteByExample(SysRoleDAOExample example);

    int deleteByPrimaryKey(String id);

    int insert(SysRoleDAO record);

    int insertSelective(SysRoleDAO record);

    List<SysRoleDAO> selectByExample(SysRoleDAOExample example);

    SysRoleDAO selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") SysRoleDAO record, @Param("example") SysRoleDAOExample example);

    int updateByExample(@Param("record") SysRoleDAO record, @Param("example") SysRoleDAOExample example);

    int updateByPrimaryKeySelective(SysRoleDAO record);

    int updateByPrimaryKey(SysRoleDAO record);
}