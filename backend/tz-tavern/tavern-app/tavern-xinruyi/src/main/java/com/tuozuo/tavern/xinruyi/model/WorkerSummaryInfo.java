package com.tuozuo.tavern.xinruyi.model;

import java.math.BigDecimal;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/10/14 <br>
 */
public class WorkerSummaryInfo {

    public static WorkerSummaryInfo defaultWorkerSummaryInfo() {
        return new WorkerSummaryInfo(0, BigDecimal.ZERO, "0");
    }

    public WorkerSummaryInfo(int projectNum, BigDecimal totalSalary, String authStatus) {
        this.projectNum = projectNum;
        this.totalSalary = totalSalary;
        this.authStatus = authStatus;
    }

    private int projectNum;
    private BigDecimal totalSalary;
    private String authStatus;

    public int getProjectNum() {
        return projectNum;
    }

    public void setProjectNum(int projectNum) {
        this.projectNum = projectNum;
    }

    public BigDecimal getTotalSalary() {
        return totalSalary;
    }

    public void setTotalSalary(BigDecimal totalSalary) {
        this.totalSalary = totalSalary;
    }

    public String getAuthStatus() {
        return authStatus;
    }

    public void setAuthStatus(String authStatus) {
        this.authStatus = authStatus;
    }
}
