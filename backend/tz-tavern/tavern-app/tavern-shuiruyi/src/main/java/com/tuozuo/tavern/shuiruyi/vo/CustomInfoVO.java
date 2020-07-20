package com.tuozuo.tavern.shuiruyi.vo;

import javax.validation.constraints.NotNull;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/7/19 <br>
 */
public class CustomInfoVO {

    @NotNull(message = "customName is not null")
    private String customName;
    @NotNull(message = "customPswd is not null")
    private String customPswd;
    @NotNull(message = "customContact is not null")
    private String customContact;
    @NotNull(message = "province is not null")
    private String province;
    @NotNull(message = "customType is not null")
    private String customType;
    @NotNull(message = "hasPaid is not null")
    private String hasPaid;

    public String getCustomName() {
        return customName;
    }

    public void setCustomName(String customName) {
        this.customName = customName;
    }

    public String getCustomPswd() {
        return customPswd;
    }

    public void setCustomPswd(String customPswd) {
        this.customPswd = customPswd;
    }

    public String getCustomContact() {
        return customContact;
    }

    public void setCustomContact(String customContact) {
        this.customContact = customContact;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCustomType() {
        return customType;
    }

    public void setCustomType(String customType) {
        this.customType = customType;
    }

    public String getHasPaid() {
        return hasPaid;
    }

    public void setHasPaid(String hasPaid) {
        this.hasPaid = hasPaid;
    }
}
