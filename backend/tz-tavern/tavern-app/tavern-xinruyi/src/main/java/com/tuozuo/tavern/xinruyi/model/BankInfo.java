package com.tuozuo.tavern.xinruyi.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("bank_info")
public class BankInfo {
    @TableId
    private String bankCode;

    private String bankName;

    private String bankLevel;

    private String bankParentCode;

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode == null ? null : bankCode.trim();
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName == null ? null : bankName.trim();
    }

    public String getBankLevel() {
        return bankLevel;
    }

    public void setBankLevel(String bankLevel) {
        this.bankLevel = bankLevel == null ? null : bankLevel.trim();
    }

    public String getBankParentCode() {
        return bankParentCode;
    }

    public void setBankParentCode(String bankParentCode) {
        this.bankParentCode = bankParentCode == null ? null : bankParentCode.trim();
    }
}