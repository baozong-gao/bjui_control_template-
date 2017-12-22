package com.shenxin.core.control.vo;

import lombok.Data;

/**
 * @Author: gaobaozong
 * @Description: 用于展示
 * @Date: Created in 2017/12/21 - 14:03
 * @Version: V1.0
 */
@Data
public class UserVO {
    private String id;

    private String name;

    private String pwd;

    private String remark;

    private String disableTag;

    private String email;

    private String type;

    //是否与用户关联
    private boolean isAuth;
}
