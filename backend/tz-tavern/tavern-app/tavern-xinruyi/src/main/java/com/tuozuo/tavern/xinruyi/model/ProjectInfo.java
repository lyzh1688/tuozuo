package com.tuozuo.tavern.xinruyi.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
@TableName("project_info")
public class ProjectInfo {
    @TableId
    private String projectId;

    private String companyId;

    private String projectIndustry;

    private String projectName;

    private LocalDateTime publishDate;

    private BigDecimal period;

    private String contractPerson;

    private String contractPhone;

    private Integer projectMemberCount;

    private String area;

    private String onSpot;

    private String fileMaterial;

    private String projectDesc;

    private String remark;

    private BigDecimal fee;

    private String status;

    private String budget;

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

    public LocalDateTime getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(LocalDateTime publishDate) {
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

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area == null ? null : area.trim();
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