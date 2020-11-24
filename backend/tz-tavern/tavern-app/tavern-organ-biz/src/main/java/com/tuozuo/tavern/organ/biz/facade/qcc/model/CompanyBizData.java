package com.tuozuo.tavern.organ.biz.facade.qcc.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/11/23 <br>
 */
public class CompanyBizData {
    @JsonProperty("KeyNo")
    private String keyNo;
    @JsonProperty("Name")
    private String name;
    @JsonProperty("OperName")
    private String operName;
    @JsonProperty("StartDate")
    private String startDate;
    @JsonProperty("Status")
    private String status;
    @JsonProperty("No")
    private String no;
    @JsonProperty("CreditCode")
    private String creditCode;

    public String getOperName() {
        return operName;
    }

    public void setOperName(String operName) {
        this.operName = operName;
    }

    public String getKeyNo() {
        return keyNo;
    }

    public void setKeyNo(String keyNo) {
        this.keyNo = keyNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getCreditCode() {
        return creditCode;
    }

    public void setCreditCode(String creditCode) {
        this.creditCode = creditCode;
    }

    @Override
    public String toString() {
        return "CompanyBizData{" +
                "keyNo='" + keyNo + '\'' +
                ", name='" + name + '\'' +
                ", operName='" + operName + '\'' +
                ", startDate='" + startDate + '\'' +
                ", status='" + status + '\'' +
                ", no='" + no + '\'' +
                ", creditCode='" + creditCode + '\'' +
                '}';
    }
}
