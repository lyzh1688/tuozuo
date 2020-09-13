package com.tuozuo.tavern.xinruyi.vo;

import javax.validation.constraints.NotNull;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/9/12 <br>
 */
public class AuditCompanySettledVO {
    @NotNull(message = "registerId is not null")
    private String registerId;
    private String companyId;
    private String password;
    @NotNull(message = "remark is not null")
    private String remark;
    @NotNull(message = "result is not null")
    private String result;

    public String getRegisterId() {
        return registerId;
    }

    public void setRegisterId(String registerId) {
        this.registerId = registerId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
