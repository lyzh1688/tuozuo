package com.tuozuo.tavern.xinruyi.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("hot_project_info")
public class HotProjectInfo {
    @TableId
    private String projectId;

    private String projectName;

    private Integer order;

    @TableField(exist = false)
    private String projectDesc;
    @TableField(exist = false)
    private String companyId;
    @TableField(exist = false)
    private String companyName;
    @TableField(exist = false)
    private int projectStatus;

    public String getProjectDesc() {
        return projectDesc;
    }

    public void setProjectDesc(String projectDesc) {
        this.projectDesc = projectDesc;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public int getProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(int projectStatus) {
        this.projectStatus = projectStatus;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId == null ? null : projectId.trim();
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName == null ? null : projectName.trim();
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }
}