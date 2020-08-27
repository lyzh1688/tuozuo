package com.tuozuo.tavern.xinruyi.model;

public class CompanyInfo {
    private String registerId;

    private String companyId;

    private String companyName;

    private String companyInfocol;

    private String companyIndustry;

    private String area;

    private String status;

    public String getRegisterId() {
        return registerId;
    }

    public void setRegisterId(String registerId) {
        this.registerId = registerId == null ? null : registerId.trim();
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId == null ? null : companyId.trim();
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName == null ? null : companyName.trim();
    }

    public String getCompanyInfocol() {
        return companyInfocol;
    }

    public void setCompanyInfocol(String companyInfocol) {
        this.companyInfocol = companyInfocol == null ? null : companyInfocol.trim();
    }

    public String getCompanyIndustry() {
        return companyIndustry;
    }

    public void setCompanyIndustry(String companyIndustry) {
        this.companyIndustry = companyIndustry == null ? null : companyIndustry.trim();
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area == null ? null : area.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }
}