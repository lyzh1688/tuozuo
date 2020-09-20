package com.tuozuo.tavern.xinruyi.model;

import com.baomidou.mybatisplus.annotation.TableName;

@TableName("project_payment_snapshot")
public class ProjectPaymentSnapshot {
    private String projectId;

    private String companyId;

    private String paymentId;

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId == null ? null : projectId.trim();
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId == null ? null : companyId.trim();
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId == null ? null : paymentId.trim();
    }
}