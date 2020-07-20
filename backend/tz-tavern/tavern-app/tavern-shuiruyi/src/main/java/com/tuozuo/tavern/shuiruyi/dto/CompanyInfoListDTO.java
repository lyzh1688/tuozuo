package com.tuozuo.tavern.shuiruyi.dto;

import java.util.List;

import com.google.common.collect.Lists;

/**
 * 功能说明: <br>
 * 系统说明: <br>
 * 模块说明: <br>
 * 功能描述: <br>
 * <br>
 *
 * @author hebiao@orientsec.com.cn
 * <br>
 * 软件著作: 东方证券 版权所有
 * @since 1.0
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
