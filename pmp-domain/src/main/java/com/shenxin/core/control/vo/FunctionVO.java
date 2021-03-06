package com.shenxin.core.control.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author: gaobaozong
 * @Description: 用于展示
 * @Date: Created in 2017/12/15 - 14:48
 * @Version: V1.0
 */
@Data
public class FunctionVO {
    private BigDecimal id;

    private String name;

    private BigDecimal parentId;

    private String grade;

    private String uri;

    private String code;

    private String status;

    private BigDecimal orderBy;

    private String logo;

    //是否与角色关联
    private boolean isAuth;
}
