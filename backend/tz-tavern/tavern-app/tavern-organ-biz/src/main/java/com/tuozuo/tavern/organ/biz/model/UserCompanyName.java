package com.tuozuo.tavern.organ.biz.model;

import java.util.List;

//用户输入公司名称
public class UserCompanyName {
    String area;//公司名称地域
    String name;//公司名称
    List<String> namePinYinList;//名称的单字拼音列表，按照原顺序排列好的
    String industryDesc;//行业描述

    @Override
    public String toString() {
        return "userCompanyName{" +
                "area='" + area + '\'' +
                ", name='" + name + '\'' +
                ", namePinYinList=" + namePinYinList +
                ", industryDesc='" + industryDesc + '\'' +
                '}';
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getNamePinYinList() {
        return namePinYinList;
    }

    public void setNamePinYinList(List<String> namePinYinList) {
        this.namePinYinList = namePinYinList;
    }

    public String getIndustryDesc() {
        return industryDesc;
    }

    public void setIndustryDesc(String industryDesc) {
        this.industryDesc = industryDesc;
    }
}
