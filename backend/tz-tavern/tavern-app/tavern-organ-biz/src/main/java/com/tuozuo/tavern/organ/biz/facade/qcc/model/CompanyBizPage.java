package com.tuozuo.tavern.organ.biz.facade.qcc.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/11/23 <br>
 */

public class CompanyBizPage {

    @JsonProperty("PageSize")
    private int pageSize;
    @JsonProperty("PageIndex")
    private int pageNo;
    @JsonProperty("TotalRecords")
    private int total;

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
