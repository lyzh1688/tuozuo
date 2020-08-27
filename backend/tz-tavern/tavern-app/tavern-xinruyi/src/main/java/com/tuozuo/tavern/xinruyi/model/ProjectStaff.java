package com.tuozuo.tavern.xinruyi.model;

import java.math.BigDecimal;
import java.util.Date;

public class ProjectStaff {
    private String projectId;

    private String staffId;

    private String isSigned;

    private Date enterDate;

    private Date quitDate;

    private BigDecimal salary;

    private String status;

    private String remark;

    private String isSettled;

    private String quitReason;

    private String staffName;

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId == null ? null : projectId.trim();
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId == null ? null : staffId.trim();
    }

    public String getIsSigned() {
        return isSigned;
    }

    public void setIsSigned(String isSigned) {
        this.isSigned = isSigned == null ? null : isSigned.trim();
    }

    public Date getEnterDate() {
        return enterDate;
    }

    public void setEnterDate(Date enterDate) {
        this.enterDate = enterDate;
    }

    public Date getQuitDate() {
        return quitDate;
    }

    public void setQuitDate(Date quitDate) {
        this.quitDate = quitDate;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getIsSettled() {
        return isSettled;
    }

    public void setIsSettled(String isSettled) {
        this.isSettled = isSettled == null ? null : isSettled.trim();
    }

    public String getQuitReason() {
        return quitReason;
    }

    public void setQuitReason(String quitReason) {
        this.quitReason = quitReason == null ? null : quitReason.trim();
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName == null ? null : staffName.trim();
    }
}