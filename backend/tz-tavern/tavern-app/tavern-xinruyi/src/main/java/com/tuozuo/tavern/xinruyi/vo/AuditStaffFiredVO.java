package com.tuozuo.tavern.xinruyi.vo;

import javax.validation.constraints.NotNull;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/9/13 <br>
 */
public class AuditStaffFiredVO {
    @NotNull(message = "companyId is not null")
    private String companyId;
    @NotNull(message = "projectId is not null")
    private String projectId;
    @NotNull(message = "staffId is not null")
    private String staffId;
    @NotNull(message = "payStatus is not null")
    private String payStatus;
    @NotNull(message = "auditResult is not null")
    private String auditResult;
    @NotNull(message = "remark is not null")
    private String remark;

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }

    public String getAuditResult() {
        return auditResult;
    }

    public void setAuditResult(String auditResult) {
        this.auditResult = auditResult;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
