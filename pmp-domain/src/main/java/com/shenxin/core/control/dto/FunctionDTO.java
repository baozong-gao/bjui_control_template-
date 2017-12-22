package com.shenxin.core.control.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author: gaobaozong
 * @Description: 权限 用于插入，更新
 * @Date: Created in 2017/12/15 - 13:26
 * @Version: V1.0
 */
@Data
public class FunctionDTO {
    private BigDecimal id;

    private String name;

    private BigDecimal parentId;

    private String grade;

    private String uri;

    private String code;

    private String status;

    private BigDecimal orderBy;

    private String logo;
}
