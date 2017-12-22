package com.shenxin.core.control.dto;

import lombok.Data;

/**
 * @Author: gaobaozong
 * @Description: 用于插入，更新
 * @Date: Created in 2017/12/19 - 10:46
 * @Version: V1.0
 */
@Data
public class SysRoleDTO {

    private String id;

    private String name;

    private String disableTag;

    private String remark;
}
