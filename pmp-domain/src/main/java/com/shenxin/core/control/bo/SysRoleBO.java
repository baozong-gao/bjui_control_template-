package com.shenxin.core.control.bo;

import com.shenxin.core.control.vo.BaseForm;
import lombok.Data;

/**
 * @Author: gaobaozong
 * @Description: 用于查询
 * @Date: Created in 2017/12/19 - 10:46
 * @Version: V1.0
 */
@Data
public class SysRoleBO extends BaseForm<SysRoleBO> {

    private String id;

    private String name;

    private String disableTag;

    private String remark;
}
