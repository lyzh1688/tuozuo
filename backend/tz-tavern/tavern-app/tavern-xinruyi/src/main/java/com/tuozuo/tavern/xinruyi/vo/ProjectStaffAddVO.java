package com.tuozuo.tavern.xinruyi.vo;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/8/29 <br>
 */
public class ProjectStaffAddVO {
    @NotNull(message = "staffId is not null")
    private String staffId;
    @NotNull(message = "salary is not null")
    private BigDecimal salary;
    private String gender;
    @NotNull(message = "enterDate is not null")
    private String enterDate;
    @NotNull(message = "quitDate is not null")
    private String quitDate;
    @NotNull(message = "remark is not null")
    private String remark;

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEnterDate() {
        return enterDate;
    }

    public void setEnterDate(String enterDate) {
        this.enterDate = enterDate;
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
