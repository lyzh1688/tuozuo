package com.tuozuo.tavern.organ.biz.model;

import com.google.common.collect.Lists;

import java.util.List;

//处理后的公司名称记录
public class RecordItem {
    String industryDesc;//记录的行业描述
    String name;//记录的公司名称
    List<String> namePinYinList = Lists.newArrayList();//记录的单字拼音列表，按照原顺序排列好的
    List<RecordMark> markers = Lists.newArrayList();//记录的标记列表
    String fullName;


    @Override
    public String toString() {
        return "RecordItem{" +
                "industryDesc='" + industryDesc + '\'' +
                ", name='" + name + '\'' +
                ", namePinYinList=" + namePinYinList +
                ", markers=" + markers +
                ", fullName='" + fullName + '\'' +
                '}';
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getIndustryDesc() {
        return industryDesc;
    }

    public void setIndustryDesc(String industryDesc) {
        this.industryDesc = industryDesc;
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

    public List<RecordMark> getMarkers() {
        return markers;
    }

    public void setMarkers(List<RecordMark> markers) {
        this.markers = markers;
    }
}
