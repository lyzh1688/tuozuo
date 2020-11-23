package com.tuozuo.tavern.organ.biz.facade.qcc.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/11/23 <br>
 */
@JsonIgnoreProperties
public class CompanyBizResult {
    @JsonProperty("OrderNumber")
    private String orderNumber;
    @JsonProperty("Result")
    private List<CompanyBizData> bizData = Lists.newArrayList();
    @JsonProperty("Status")
    private int status;
    @JsonProperty("Message")
    private String message;

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public List<CompanyBizData> getBizData() {
        return bizData;
    }

    public void setBizData(List<CompanyBizData> bizData) {
        this.bizData = bizData;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
