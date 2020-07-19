package com.tuozuo.tavern.shuiruyi.dto;

import java.math.BigDecimal;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/7/18 <br>
 */
public class CustomInfoDetailDTO {

    private String customName;
    private String customContact;
    private BigDecimal totalServerCharge;
    private BigDecimal balance;
    private String province;

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getCustomName() {
        return customName;
    }

    public void setCustomName(String customName) {
        this.customName = customName;
    }

    public String getCustomContact() {
        return customContact;
    }

    public void setCustomContact(String customContact) {
        this.customContact = customContact;
    }

    public BigDecimal getTotalServerCharge() {
        return totalServerCharge;
    }

    public void setTotalServerCharge(BigDecimal totalServerCharge) {
        this.totalServerCharge = totalServerCharge;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }
}
