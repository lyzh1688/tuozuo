package com.tuozuo.tavern.shuiruyi.model;

import java.math.BigDecimal;
import java.util.Date;

public class CustomInfo {
    private String customId;

    private String customName;

    private String customPswd;

    private String customContact;

    private BigDecimal balance;

    private String province;

    private Date updateDate;

    private String customType;

    private String hasPaid;

    public String getCustomId() {
        return customId;
    }

    public void setCustomId(String customId) {
        this.customId = customId == null ? null : customId.trim();
    }

    public String getCustomName() {
        return customName;
    }

    public void setCustomName(String customName) {
        this.customName = customName == null ? null : customName.trim();
    }

    public String getCustomPswd() {
        return customPswd;
    }

    public void setCustomPswd(String customPswd) {
        this.customPswd = customPswd == null ? null : customPswd.trim();
    }

    public String getCustomContact() {
        return customContact;
    }

    public void setCustomContact(String customContact) {
        this.customContact = customContact == null ? null : customContact.trim();
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getCustomType() {
        return customType;
    }

    public void setCustomType(String customType) {
        this.customType = customType == null ? null : customType.trim();
    }

    public String getHasPaid() {
        return hasPaid;
    }

    public void setHasPaid(String hasPaid) {
        this.hasPaid = hasPaid == null ? null : hasPaid.trim();
    }
}