package com.tuozuo.tavern.xinruyi.model;

public class ProjectPayment {
    private String paymentId;

    private String companyId;

    private String projectId;

    private String period;

    private String fileVoucher;

    private String filePayCert;

    private String status;

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId == null ? null : paymentId.trim();
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId == null ? null : companyId.trim();
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId == null ? null : projectId.trim();
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period == null ? null : period.trim();
    }

    public String getFileVoucher() {
        return fileVoucher;
    }

    public void setFileVoucher(String fileVoucher) {
        this.fileVoucher = fileVoucher == null ? null : fileVoucher.trim();
    }

    public String getFilePayCert() {
        return filePayCert;
    }

    public void setFilePayCert(String filePayCert) {
        this.filePayCert = filePayCert == null ? null : filePayCert.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }
}