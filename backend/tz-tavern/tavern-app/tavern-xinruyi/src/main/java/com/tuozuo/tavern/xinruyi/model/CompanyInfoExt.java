package com.tuozuo.tavern.xinruyi.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("company_info_ext")
public class CompanyInfoExt {

    @TableField(exist = false)
    private String registerId;
    @TableField(exist = false)
    private String companyName;

    @TableId
    private String companyId;

    private String fileBizLicense;

    private String legalPersonIdentity;

    private String legalPersonName;

    private String fileLegalPersonIdcardUp;

    private String fileLegalPersonIdcardBack;

    private String companyBankAccount;

    private String companyBank;

    private String companyBranchBank;

    private String contactName;

    private String contactPhone;

    private String remark;

    public String getCompanyBranchBank() {
        return companyBranchBank;
    }

    public void setCompanyBranchBank(String companyBranchBank) {
        this.companyBranchBank = companyBranchBank;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getRegisterId() {
        return registerId;
    }

    public void setRegisterId(String registerId) {
        this.registerId = registerId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getFileBizLicense() {
        return fileBizLicense;
    }

    public void setFileBizLicense(String fileBizLicense) {
        this.fileBizLicense = fileBizLicense;
    }

    public String getLegalPersonIdentity() {
        return legalPersonIdentity;
    }

    public void setLegalPersonIdentity(String legalPersonIdentity) {
        this.legalPersonIdentity = legalPersonIdentity;
    }

    public String getLegalPersonName() {
        return legalPersonName;
    }

    public void setLegalPersonName(String legalPersonName) {
        this.legalPersonName = legalPersonName;
    }

    public String getFileLegalPersonIdcardUp() {
        return fileLegalPersonIdcardUp;
    }

    public void setFileLegalPersonIdcardUp(String fileLegalPersonIdcardUp) {
        this.fileLegalPersonIdcardUp = fileLegalPersonIdcardUp;
    }

    public String getFileLegalPersonIdcardBack() {
        return fileLegalPersonIdcardBack;
    }

    public void setFileLegalPersonIdcardBack(String fileLegalPersonIdcardBack) {
        this.fileLegalPersonIdcardBack = fileLegalPersonIdcardBack;
    }

    public String getCompanyBankAccount() {
        return companyBankAccount;
    }

    public void setCompanyBankAccount(String companyBankAccount) {
        this.companyBankAccount = companyBankAccount;
    }

    public String getCompanyBank() {
        return companyBank;
    }

    public void setCompanyBank(String companyBank) {
        this.companyBank = companyBank;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}