package com.tuozuo.tavern.shuiruyi.dto;

import java.util.List;

import com.google.common.collect.Lists;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/7/18 <br>
 */
public class CompanyInfoListDTO {
    private List<CompanyBriefInfo> companies = Lists.newArrayList();
    private int total;

    public List<CompanyBriefInfo> getCompanies() {
        return companies;
    }

    public void setCompanies(List<CompanyBriefInfo> companies) {
        this.companies = companies;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
