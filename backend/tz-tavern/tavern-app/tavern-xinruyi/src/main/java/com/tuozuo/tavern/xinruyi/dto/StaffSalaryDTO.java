package com.tuozuo.tavern.xinruyi.dto;

import java.math.BigDecimal;

public class StaffSalaryDTO {
    private String staffId;
    private BigDecimal salary;

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }
}