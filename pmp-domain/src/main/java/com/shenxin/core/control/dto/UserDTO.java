package com.shenxin.core.control.dto;

import lombok.Data;

import java.util.Date;

/**
 * @Author: gaobaozong
 * @Description: 用于插入，更新
 * @Date: Created in 2017/12/21 - 14:03
 * @Version: V1.0
 */
@Data
public class UserDTO {
    private String id;

    private String name;

    private String pwd;

    private String remark;

    private String disableTag;

    private String email;

    private String createBy;

    private Date createDate;

    private String updateBy;

    private Date updateDate;

    private String type;
}
