package com.tuozuo.tavern.xinruyi.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.time.LocalDate;

@TableName("project_info")
public class ProjectInfo {
    @TableId
    private String projectId;

    private String companyId;

    @TableField(exist = false)
    private String companyName;
    @TableField(exist = false)
    private String companyLogo;
    @TableField(exist = false)
    private String industryName;

    private String projectIndustry;

    private String projectName;

    private LocalDate publishDate;

    private BigDecimal period;

    private String contractPerson;

    private String contractPhone;

    private Integer projectMemberCount;

    private String province;
    private String city;
    private String district;

    private String onSpot;

    private String fileMaterial;

    private String projectDesc;

    private String remark;

    private BigDecimal fee;

    private String status;
    @TableField(exist = false)
    private String statusDesc;
    @TableField(exist = false)
    private LocalDate enterDate;
    @TableField(exist = false)
    private LocalDate quitDate;

    private String budget;
    @TableField(exist = false)
    private long workDays;


    public String getIndustryName() {
        return industryName;
    }

    public void setIndustryName(String industryName) {
        this.industryName = industryName;
    }

    public long getWorkDays() {
        return workDays;
    }

    public void setWorkDays(long workDays) {
        this.workDays = workDays;
    }

    public LocalDate getEnterDate() {
        return enterDate;
    }

    public void setEnterDate(LocalDate enterDate) {
        this.enterDate = enterDate;
    }

    public LocalDate getQuitDate() {
        return quitDate;
    }

    public void setQuitDate(LocalDate quitDate) {
        this.quitDate = quitDate;
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }

    public String getCompanyLogo() {
        return companyLogo;
    }

    public void setCompanyLogo(String companyLogo) {
        this.companyLogo = companyLogo;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

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

    public String getProjectIndustry() {
        return projectIndustry;
    }

    public void setProjectIndustry(String projectIndustry) {
        this.projectIndustry = projectIndustry == null ? null : projectIndustry.trim();
    }

    public LocalDate getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(LocalDate publishDate) {
        this.publishDate = publishDate;
    }

    public BigDecimal getPeriod() {
        return period;
    }

    public void setPeriod(BigDecimal period) {
        this.period = period;
    }

    public String getContractPerson() {
        return contractPerson;
    }

    public void setContractPerson(String contractPerson) {
        this.contractPerson = contractPerson == null ? null : contractPerson.trim();
    }

    public String getContractPhone() {
        return contractPhone;
    }

    public void setContractPhone(String contractPhone) {
        this.contractPhone = contractPhone == null ? null : contractPhone.trim();
    }

    public Integer getProjectMemberCount() {
        return projectMemberCount;
    }

    public void setProjectMemberCount(Integer projectMemberCount) {
        this.projectMemberCount = projectMemberCount;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getOnSpot() {
        return onSpot;
    }

    public void setOnSpot(String onSpot) {
        this.onSpot = onSpot == null ? null : onSpot.trim();
    }

    public String getFileMaterial() {
        return fileMaterial;
    }

    public void setFileMaterial(String fileMaterial) {
        this.fileMaterial = fileMaterial == null ? null : fileMaterial.trim();
    }

    public String getProjectDesc() {
        return projectDesc;
    }

    public void setProjectDesc(String projectDesc) {
        this.projectDesc = projectDesc == null ? null : projectDesc.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget == null ? null : budget.trim();
    }
}