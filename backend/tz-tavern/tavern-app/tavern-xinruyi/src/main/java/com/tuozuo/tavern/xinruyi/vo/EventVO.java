package com.tuozuo.tavern.xinruyi.vo;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/9/10 <br>
 */
public class EventVO extends PageVO{
    private String companyId;
    private String projectId;
    private String eventId;
    private String status;

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

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
