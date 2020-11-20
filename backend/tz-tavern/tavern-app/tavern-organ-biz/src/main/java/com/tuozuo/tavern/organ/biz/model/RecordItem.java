package com.tuozuo.tavern.organ.biz.model;

import java.util.List;

//处理后的公司名称记录
public class RecordItem {
    String industryDesc;//记录的行业描述
    String name;//记录的公司名称
    List<String> namePinYingList;//记录的单字拼音列表，按照原顺序排列好的
    List<RecordMark> markers;//记录的标记列表

    @Override
    public String toString() {
        return "recordItem{" +
                "industryDesc='" + industryDesc + '\'' +
                ", name='" + name + '\'' +
                ", namePinYingList=" + namePinYingList +
                ", markers=" + markers +
                '}';
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

    public List<String> getNamePinYingList() {
        return namePinYingList;
    }

    public void setNamePinYingList(List<String> namePinYingList) {
        this.namePinYingList = namePinYingList;
    }

    public List<RecordMark> getMarkers() {
        return markers;
    }

    public void setMarkers(List<RecordMark> markers) {
        this.markers = markers;
    }
}
