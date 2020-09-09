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

  /*  companyId	String	公司Id
    companyName	String	公司名称
    projectId	String	项目Id
    projectName	String	项目名称
    applyDate	String	申请日期
    projectStatus	String	申请状态
    rate	number	服务费率
    industryType	String	行业分类
    projectCycle	String	项目周期
    releaseDate	String	发布日期
    staffNum	number	项目人员
    city	String	所在城市	字典？
    contact	String	联系电话
    isOnSite	String	是否驻场	0，否，1，是
    contactName	String	项目联系人
    projectFile	String	项目资料
    remark	String	备注*/


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
