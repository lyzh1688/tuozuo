package com.tuozuo.tavern.organ.biz.dto;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/11/26 <br>
 */
public class CompanyPropertyDTO {
    private String superClass;
    private List<String> subClass = Lists.newArrayList();

    public String getSuperClass() {
        return superClass;
    }

    public void setSuperClass(String superClass) {
        this.superClass = superClass;
    }

    public List<String> getSubClass() {
        return subClass;
    }

    public void setSubClass(List<String> subClass) {
        this.subClass = subClass;
    }
}
