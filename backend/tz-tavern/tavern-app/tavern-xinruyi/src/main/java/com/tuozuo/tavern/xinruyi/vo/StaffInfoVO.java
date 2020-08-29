package com.tuozuo.tavern.xinruyi.vo;

import javax.validation.constraints.NotNull;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/8/28 <br>
 */
public class StaffInfoVO {

    @NotNull(message = "name is not null")
    private String name;
    @NotNull(message = "idNo is not null")
    private String idNo;
    @NotNull(message = "gender is not null")
    private String gender;
    @NotNull(message = "bankCard is not null")
    private String bankCard;
    @NotNull(message = "bank is not null")
    private String bank;
    @NotNull(message = "accntBank is not null")
    private String accntBank;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBankCard() {
        return bankCard;
    }

    public void setBankCard(String bankCard) {
        this.bankCard = bankCard;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getAccntBank() {
        return accntBank;
    }

    public void setAccntBank(String accntBank) {
        this.accntBank = accntBank;
    }
}
