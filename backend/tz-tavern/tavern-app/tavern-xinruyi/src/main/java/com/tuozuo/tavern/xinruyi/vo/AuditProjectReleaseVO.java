package com.tuozuo.tavern.xinruyi.vo;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/9/13 <br>
 */
public class AuditProjectReleaseVO {

    @NotNull(message = "projectId is not null")
    private String projectId;
    private BigDecimal fee;
    @NotNull(message = "status is not null")
    private String status;
    @NotNull(message = "remark is not null")
    private String remark;

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
