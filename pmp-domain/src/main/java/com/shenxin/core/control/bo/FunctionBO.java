package com.shenxin.core.control.bo;

import com.shenxin.core.control.vo.BaseForm;
import lombok.Data;

/**
 * @Author: gaobaozong
 * @Description: 用于查询
 * @Date: Created in 2017/12/15 - 11:07
 * @Version: V1.0
 */
@Data
public class FunctionBO extends BaseForm<FunctionBO> {
    private String id;

    private String name;

    private String parentId;

    private String grade;

    private String code;

    private String status;
}
