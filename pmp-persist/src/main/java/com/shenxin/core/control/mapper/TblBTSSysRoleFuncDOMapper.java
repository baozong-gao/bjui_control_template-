package com.shenxin.core.control.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.shenxin.core.control.entity.TblBTSSysRoleFuncDO;
import com.shenxin.core.control.entity.TblBTSSysRoleFuncDOExample;
import com.shenxin.core.control.entity.TblBTSSysRoleFuncDOKey;

public interface TblBTSSysRoleFuncDOMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TBL_BTS_SYS_ROLE_FUNC
     *
     * @mbggenerated
     */
    int countByExample(TblBTSSysRoleFuncDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TBL_BTS_SYS_ROLE_FUNC
     *
     * @mbggenerated
     */
    int deleteByExample(TblBTSSysRoleFuncDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TBL_BTS_SYS_ROLE_FUNC
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(TblBTSSysRoleFuncDOKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TBL_BTS_SYS_ROLE_FUNC
     *
     * @mbggenerated
     */
    int insert(TblBTSSysRoleFuncDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TBL_BTS_SYS_ROLE_FUNC
     *
     * @mbggenerated
     */
    int insertSelective(TblBTSSysRoleFuncDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TBL_BTS_SYS_ROLE_FUNC
     *
     * @mbggenerated
     */
    List<TblBTSSysRoleFuncDO> selectByExample(TblBTSSysRoleFuncDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TBL_BTS_SYS_ROLE_FUNC
     *
     * @mbggenerated
     */
    TblBTSSysRoleFuncDO selectByPrimaryKey(TblBTSSysRoleFuncDOKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TBL_BTS_SYS_ROLE_FUNC
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") TblBTSSysRoleFuncDO record,
                                 @Param("example") TblBTSSysRoleFuncDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TBL_BTS_SYS_ROLE_FUNC
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") TblBTSSysRoleFuncDO record,
                        @Param("example") TblBTSSysRoleFuncDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TBL_BTS_SYS_ROLE_FUNC
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(TblBTSSysRoleFuncDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TBL_BTS_SYS_ROLE_FUNC
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(TblBTSSysRoleFuncDO record);

    int deleteAllAuthory(String roleId);

    int insertBatch(List<TblBTSSysRoleFuncDO> roleFuncDOList);
}