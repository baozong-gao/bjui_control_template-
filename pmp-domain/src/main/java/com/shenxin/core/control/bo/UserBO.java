package com.shenxin.core.control.bo;

import com.shenxin.core.control.vo.BaseForm;
import lombok.Data;

import java.util.Date;

/**
 * @Author: gaobaozong
 * @Description: 用于查询
 * @Date: Created in 2017/12/21 - 14:02
 * @Version: V1.0
 */
@Data
public class UserBO extends BaseForm<UserBO> {

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
