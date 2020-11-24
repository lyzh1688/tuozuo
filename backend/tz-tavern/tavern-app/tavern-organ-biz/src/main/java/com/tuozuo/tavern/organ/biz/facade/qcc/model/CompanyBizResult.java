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
    @JsonProperty("Paging")
    private CompanyBizPage page;
    @JsonProperty("OrderNumber")
    private String orderNumber;
    @JsonProperty("Result")
    private List<CompanyBizData> bizData = Lists.newArrayList();
    @JsonProperty("Status")
    private String status;
    @JsonProperty("Message")
    private String message;

    public CompanyBizPage getPage() {
        return page;
    }

    public void setPage(CompanyBizPage page) {
        this.page = page;
    }

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "CompanyBizResult{" +
                "orderNumber='" + orderNumber + '\'' +
                ", bizData=" + bizData +
                ", status='" + status + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
