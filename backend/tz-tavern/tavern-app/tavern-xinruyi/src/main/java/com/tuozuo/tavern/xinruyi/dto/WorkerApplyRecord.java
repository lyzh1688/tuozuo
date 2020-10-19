package com.tuozuo.tavern.xinruyi.dto;

import org.apache.commons.lang3.StringUtils;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/10/17 <br>
 */
public class WorkerApplyRecord {
    private String eventId;
    private String eventDate;
    private String companyName;
    private String projectName;
    private String status;
    private String statusDesc;
    private String remark;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc() {
        if (StringUtils.isEmpty(this.status)) {
            this.statusDesc = null;
        }
        if (this.status.equals("0")) {
            this.statusDesc = "申请失败";
        }
        if (this.status.equals("1")) {
            this.statusDesc = "申请成功";
        }
        if (this.status.equals("2")) {
            this.statusDesc = "申请中";
        }
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
