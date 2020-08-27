package com.tuozuo.tavern.xinruyi.model;

public class CompanyInfoExt {
    private String companyId;

    private String fileBizLicense;

    private String legalPersonIdentity;

    private String legalPersonName;

    private String fileLegalPersonIdcardUp;

    private String fileLegalPersionIdcardBack;

    private String companyBankAccount;

    private String companyBank;

    private String contactName;

    private String contactPhone;

    private String remark;

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId == null ? null : companyId.trim();
    }

    public String getFileBizLicense() {
        return fileBizLicense;
    }

    public void setFileBizLicense(String fileBizLicense) {
        this.fileBizLicense = fileBizLicense == null ? null : fileBizLicense.trim();
    }

    public String getLegalPersonIdentity() {
        return legalPersonIdentity;
    }

    public void setLegalPersonIdentity(String legalPersonIdentity) {
        this.legalPersonIdentity = legalPersonIdentity == null ? null : legalPersonIdentity.trim();
    }

    public String getLegalPersonName() {
        return legalPersonName;
    }

    public void setLegalPersonName(String legalPersonName) {
        this.legalPersonName = legalPersonName == null ? null : legalPersonName.trim();
    }

    public String getFileLegalPersonIdcardUp() {
        return fileLegalPersonIdcardUp;
    }

    public void setFileLegalPersonIdcardUp(String fileLegalPersonIdcardUp) {
        this.fileLegalPersonIdcardUp = fileLegalPersonIdcardUp == null ? null : fileLegalPersonIdcardUp.trim();
    }

    public String getFileLegalPersionIdcardBack() {
        return fileLegalPersionIdcardBack;
    }

    public void setFileLegalPersionIdcardBack(String fileLegalPersionIdcardBack) {
        this.fileLegalPersionIdcardBack = fileLegalPersionIdcardBack == null ? null : fileLegalPersionIdcardBack.trim();
    }

    public String getCompanyBankAccount() {
        return companyBankAccount;
    }

    public void setCompanyBankAccount(String companyBankAccount) {
        this.companyBankAccount = companyBankAccount == null ? null : companyBankAccount.trim();
    }

    public String getCompanyBank() {
        return companyBank;
    }

    public void setCompanyBank(String companyBank) {
        this.companyBank = companyBank == null ? null : companyBank.trim();
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName == null ? null : contactName.trim();
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone == null ? null : contactPhone.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}