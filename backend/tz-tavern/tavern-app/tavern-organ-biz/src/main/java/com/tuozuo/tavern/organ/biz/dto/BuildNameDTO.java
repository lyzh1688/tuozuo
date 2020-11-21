package com.tuozuo.tavern.organ.biz.dto;

import com.google.common.collect.Lists;
import com.tuozuo.tavern.organ.biz.model.CompanyName;

import java.util.List;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/11/21 <br>
 */
public class BuildNameDTO {
    private List<CompanyName> names = Lists.newArrayList();
    private int totalNum;

    public List<CompanyName> getNames() {
        return names;
    }

    public void setNames(List<CompanyName> names) {
        this.names = names;
    }

    public int getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }
}
