package com.tuozuo.tavern.organ.biz.model;

import java.util.List;

//用户输入公司名称
public class UserCompanyName {
    String area;//公司名称地域
    String name;//公司名称
    List<String> namePinYingList;//名称的单字拼音列表，按照原顺序排列好的
    String industryDesc;//行业描述

    @Override
    public String toString() {
        return "userCompanyName{" +
                "area='" + area + '\'' +
                ", name='" + name + '\'' +
                ", namePinYingList=" + namePinYingList +
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

    public List<String> getNamePinYingList() {
        return namePinYingList;
    }

    public void setNamePinYingList(List<String> namePinYingList) {
        this.namePinYingList = namePinYingList;
    }

    public String getIndustryDesc() {
        return industryDesc;
    }

    public void setIndustryDesc(String industryDesc) {
        this.industryDesc = industryDesc;
    }
}
