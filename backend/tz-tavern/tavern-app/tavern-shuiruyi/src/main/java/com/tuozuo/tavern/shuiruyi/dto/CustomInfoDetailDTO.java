package com.tuozuo.tavern.shuiruyi.dto;

import java.math.BigDecimal;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/7/18 <br>
 */
public class CustomInfoDetailDTO {

    private String customType;
    private String customName;
    private String customContact;
    private BigDecimal totalServerCharge;
    private BigDecimal balance;
    private String province;
    private String city;
    private String district;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getCustomType() {
        return customType;
    }

    public void setCustomType(String customType) {
        this.customType = customType;
    }

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
