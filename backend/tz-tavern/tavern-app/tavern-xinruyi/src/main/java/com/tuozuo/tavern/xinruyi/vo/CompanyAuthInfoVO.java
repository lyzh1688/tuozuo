package com.tuozuo.tavern.xinruyi.vo;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/9/5 <br>
 */
public class CompanyAuthInfoVO {

    private String companyId;
    @NotNull(message = "companyName is not null")
    private String companyName;
    private MultipartFile businessLicense;
    @NotNull(message = "bossName is not null")
    private String bossName;
    @NotNull(message = "bossId is not null")
    private String bossId;
    private MultipartFile bossIdPicUp;
    private MultipartFile bossIdPicBack;
    private MultipartFile companyLogo;
    @NotNull(message = "companyAccount is not null")
    private String companyAccount;
    @NotNull(message = "companyAccountBank is not null")
    private String companyAccountBank;
    @NotNull(message = "companyAccountBranchBank is not null")
    private String companyAccountBranchBank;
    @NotNull(message = "contactName is not null")
    private String contactName;
    @NotNull(message = "contact is not null")
    private String contact;

    public MultipartFile getCompanyLogo() {
        return companyLogo;
    }

    public void setCompanyLogo(MultipartFile companyLogo) {
        this.companyLogo = companyLogo;
    }

    public String getCompanyAccountBranchBank() {
        return companyAccountBranchBank;
    }

    public void setCompanyAccountBranchBank(String companyAccountBranchBank) {
        this.companyAccountBranchBank = companyAccountBranchBank;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public MultipartFile getBusinessLicense() {
        return businessLicense;
    }

    public void setBusinessLicense(MultipartFile businessLicense) {
        this.businessLicense = businessLicense;
    }

    public String getBossName() {
        return bossName;
    }

    public void setBossName(String bossName) {
        this.bossName = bossName;
    }

    public String getBossId() {
        return bossId;
    }

    public void setBossId(String bossId) {
        this.bossId = bossId;
    }

    public MultipartFile getBossIdPicUp() {
        return bossIdPicUp;
    }

    public void setBossIdPicUp(MultipartFile bossIdPicUp) {
        this.bossIdPicUp = bossIdPicUp;
    }

    public MultipartFile getBossIdPicBack() {
        return bossIdPicBack;
    }

    public void setBossIdPicBack(MultipartFile bossIdPicBack) {
        this.bossIdPicBack = bossIdPicBack;
    }

    public String getCompanyAccount() {
        return companyAccount;
    }

    public void setCompanyAccount(String companyAccount) {
        this.companyAccount = companyAccount;
    }

    public String getCompanyAccountBank() {
        return companyAccountBank;
    }

    public void setCompanyAccountBank(String companyAccountBank) {
        this.companyAccountBank = companyAccountBank;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

}
