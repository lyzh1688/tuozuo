package com.tuozuo.tavern.xinruyi.dto;

import java.math.BigDecimal;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/8/30 <br>
 */
public class ProjectInfoDTO {
    private String companyName;
    private String projectId;
    private String projectName;
    private String projectCycle;
    private BigDecimal budget;
    private BigDecimal staffNum;
    private String beginDate;
    private String endDate;
    private String projectStatus;
    private String industryType;
    private String companyLogo;
    private String projectDesc;
    private String projectStatusDesc;

    public String getCompanyLogo() {
        return companyLogo;
    }

    public void setCompanyLogo(String companyLogo) {
        this.companyLogo = companyLogo;
    }

    public String getProjectDesc() {
        return projectDesc;
    }

    public void setProjectDesc(String projectDesc) {
        this.projectDesc = projectDesc;
    }

    public String getProjectStatusDesc() {
        return projectStatusDesc;
    }

    public void setProjectStatusDesc(String projectStatusDesc) {
        this.projectStatusDesc = projectStatusDesc;
    }

    public String getIndustryType() {
        return industryType;
    }

    public void setIndustryType(String industryType) {
        this.industryType = industryType;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
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

    public String getProjectCycle() {
        return projectCycle;
    }

    public void setProjectCycle(String projectCycle) {
        this.projectCycle = projectCycle;
    }

    public BigDecimal getBudget() {
        return budget;
    }

    public void setBudget(BigDecimal budget) {
        this.budget = budget;
    }

    public BigDecimal getStaffNum() {
        return staffNum;
    }

    public void setStaffNum(BigDecimal staffNum) {
        this.staffNum = staffNum;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(String projectStatus) {
        this.projectStatus = projectStatus;
    }
}
