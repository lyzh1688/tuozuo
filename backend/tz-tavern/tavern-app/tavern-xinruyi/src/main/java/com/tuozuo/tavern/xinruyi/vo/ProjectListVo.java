package com.tuozuo.tavern.xinruyi.vo;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/8/30 <br>
 */
public class ProjectListVo extends PageVO {

    private String projectId;
    private String industryType;
    private String budget;
    private String requirementStatus;

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getIndustryType() {
        return industryType;
    }

    public void setIndustryType(String industryType) {
        this.industryType = industryType;
    }

    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }

    public String getRequirementStatus() {
        return requirementStatus;
    }

    public void setRequirementStatus(String requirementStatus) {
        this.requirementStatus = requirementStatus;
    }
}
