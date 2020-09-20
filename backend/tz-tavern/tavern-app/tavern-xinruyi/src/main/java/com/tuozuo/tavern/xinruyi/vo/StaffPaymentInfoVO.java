package com.tuozuo.tavern.xinruyi.vo;

import com.google.common.collect.Lists;
import com.tuozuo.tavern.xinruyi.dto.StaffSalaryDTO;

import java.util.List;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/9/20 <br>
 */
public class StaffPaymentInfoVO {

    private String companyId;
    private String projectId;
    private String period;
    private String payDate;
    private String paymentId;
    private List<StaffSalaryDTO> staffSalary = Lists.newArrayList();

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getPayDate() {
        return payDate;
    }

    public void setPayDate(String payDate) {
        this.payDate = payDate;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public List<StaffSalaryDTO> getStaffSalary() {
        return staffSalary;
    }

    public void setStaffSalary(List<StaffSalaryDTO> staffSalary) {
        this.staffSalary = staffSalary;
    }
}

