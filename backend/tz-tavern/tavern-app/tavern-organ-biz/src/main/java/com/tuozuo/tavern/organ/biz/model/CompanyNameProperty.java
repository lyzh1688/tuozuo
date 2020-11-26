package com.tuozuo.tavern.organ.biz.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

@TableName("company_name_property")
public class CompanyNameProperty extends Model<CompanyNameProperty> {
    private String superClass;

    private String subClass;

    private String type;

    public String getSuperClass() {
        return superClass;
    }

    public void setSuperClass(String superClass) {
        this.superClass = superClass == null ? null : superClass.trim();
    }

    public String getSubClass() {
        return subClass;
    }

    public void setSubClass(String subClass) {
        this.subClass = subClass == null ? null : subClass.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }
}