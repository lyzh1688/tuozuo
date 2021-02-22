package com.tuozuo.tavern.corp.assist.model;

import com.baomidou.mybatisplus.annotation.TableName;

@TableName("business_dict")
public class BusinessDict {
    private String businessId;

    private String businessGroup;

    private String businessName;

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId == null ? null : businessId.trim();
    }

    public String getBusinessGroup() {
        return businessGroup;
    }

    public void setBusinessGroup(String businessGroup) {
        this.businessGroup = businessGroup == null ? null : businessGroup.trim();
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName == null ? null : businessName.trim();
    }
}