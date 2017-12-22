package com.shenxin.core.control.vo;

import lombok.Data;

/**
 * @Author: gaobaozong
 * @Description: 用于展示
 * @Date: Created in 2017/12/19 - 10:46
 * @Version: V1.0
 */
@Data
public class SysRoleVO {

    private String id;

    private String name;

    private String disableTag;

    private String remark;

    //是否与角色关联
    private boolean isAuth;
}
