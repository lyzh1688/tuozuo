package com.tuozuo.tavern.xinruyi.dto;

import com.google.common.collect.Lists;
import com.tuozuo.tavern.xinruyi.model.CompanyEventInfo;

import java.util.List;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/9/9 <br>
 */
public class CompanyEventListDTO {
    private int total;
    private List<CompanyEventInfo> companies = Lists.newArrayList();

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<CompanyEventInfo> getCompanies() {
        return companies;
    }

    public void setCompanies(List<CompanyEventInfo> companies) {
        this.companies = companies;
    }
}
