package com.tuozuo.tavern.xinruyi.vo;

import javax.validation.constraints.NotNull;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/10/15 <br>
 */
public class ProjectQuitVO {
    @NotNull(message = "projectId is not null")
    private String projectId;
    private String reason;

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
