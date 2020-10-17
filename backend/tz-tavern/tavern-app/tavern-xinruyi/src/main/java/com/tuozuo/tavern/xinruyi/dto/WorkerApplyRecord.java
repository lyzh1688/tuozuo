package com.tuozuo.tavern.xinruyi.dto;

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
