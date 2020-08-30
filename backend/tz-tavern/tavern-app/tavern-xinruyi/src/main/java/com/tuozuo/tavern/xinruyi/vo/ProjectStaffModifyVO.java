package com.tuozuo.tavern.xinruyi.vo;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/8/29 <br>
 */
public class ProjectStaffModifyVO {
    @NotNull(message = "salary is not null")
    private BigDecimal salary;
    @NotNull(message = "quitDate is not null")
    private String quitDate;
    @NotNull(message = "projectId is not null")
    private String projectId;
    @NotNull(message = "remark is not null")
    private String remark;

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public String getQuitDate() {
        return quitDate;
    }

    public void setQuitDate(String quitDate) {
        this.quitDate = quitDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
