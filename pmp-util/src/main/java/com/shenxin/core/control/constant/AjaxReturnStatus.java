package com.shenxin.core.control.constant;

import lombok.Data;

@Data
public class AjaxReturnStatus {

    public int     statusCode;

    public String  message;

    public boolean closeCurrent;

    public String  forward;

    public String  forwardConfirm;

}
