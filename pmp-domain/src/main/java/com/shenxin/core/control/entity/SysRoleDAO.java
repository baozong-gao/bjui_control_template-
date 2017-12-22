package com.shenxin.core.control.entity;

public class SysRoleDAO {
    private String id;

    private String name;

    private String disableTag;

    private String remark;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getDisableTag() {
        return disableTag;
    }

    public void setDisableTag(String disableTag) {
        this.disableTag = disableTag == null ? null : disableTag.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}