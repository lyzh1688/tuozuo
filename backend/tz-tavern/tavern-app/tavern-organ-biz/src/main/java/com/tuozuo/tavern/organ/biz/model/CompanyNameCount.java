package com.tuozuo.tavern.organ.biz.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

@TableName("company_name_count")
public class CompanyNameCount extends Model<CompanyNameCount> {
    @TableId
    private String pinyin;
    private String name;

    private Integer queryCnt;

    public CompanyNameCount(String pinyin, String name, Integer queryCnt) {
        this.pinyin = pinyin;
        this.name = name;
        this.queryCnt = queryCnt;
    }

    public CompanyNameCount() {
    }

    public static CompanyNameCount create(String pinyin, String name) {
        return new CompanyNameCount(pinyin, name, 0);
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin == null ? null : pinyin.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getQueryCnt() {
        return queryCnt;
    }

    public void setQueryCnt(Integer queryCnt) {
        this.queryCnt = queryCnt;
    }
}