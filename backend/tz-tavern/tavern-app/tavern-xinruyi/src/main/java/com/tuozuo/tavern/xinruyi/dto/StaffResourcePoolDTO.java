package com.tuozuo.tavern.xinruyi.dto;

import com.baomidou.mybatisplus.annotation.TableField;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/8/28 <br>
 */
public class StaffResourcePoolDTO {
    private String name;
    private String id;
    private String idNo;
    private String gender;
    private String bankCard;
    private String bank;
    private String accntBank;
    private String bankName;
    private String bankBranchName;

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankBranchName() {
        return bankBranchName;
    }

    public void setBankBranchName(String bankBranchName) {
        this.bankBranchName = bankBranchName;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
}
