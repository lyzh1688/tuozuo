package com.tuozuo.tavern.shuiruyi.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@TableName("company_info")
public class CompanyInfo extends Model<CompanyInfo> {

    @TableId
    String companyId;

    String companyStatus;

    String customId;

    String companyName;

    String companyType;

    BigDecimal tax;

    String address;

    int freeDelivery;

    String includeCancel;

    LocalDateTime beginDate;

    LocalDateTime endDate;

    String bossName;

    String bossId;

    String bossContact;

    String bossIdPicUp;

    String bossIdPicBack;

    String cfoName;

    String cfoId;

    String cfoContact;

    String cfoIdPicUp;

    String cfoIdPicBack;

    String tradeFlow;

    String registerArea;

    BigDecimal rebateTaxRate;

    public String getTradeFlow() {
        return tradeFlow;
    }

    public void setTradeFlow(String tradeFlow) {
        this.tradeFlow = tradeFlow;
    }

    public String getRegisterArea() {
        return registerArea;
    }

    public void setRegisterArea(String registerArea) {
        this.registerArea = registerArea;
    }

    public BigDecimal getRebateTaxRate() {
        return rebateTaxRate;
    }

    public void setRebateTaxRate(BigDecimal rebateTaxRate) {
        this.rebateTaxRate = rebateTaxRate;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId == null ? null : companyId.trim();
    }

    public String getCompanyStatus() {
        return companyStatus;
    }

    public void setCompanyStatus(String companyStatus) {
        this.companyStatus = companyStatus == null ? null : companyStatus.trim();
    }

    public String getCustomId() {
        return customId;
    }

    public void setCustomId(String customId) {
        this.customId = customId == null ? null : customId.trim();
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName == null ? null : companyName.trim();
    }

    public String getCompanyType() {
        return companyType;
    }

    public void setCompanyType(String companyType) {
        this.companyType = companyType == null ? null : companyType.trim();
    }

    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public int getFreeDelivery() {
        return freeDelivery;
    }

    public void setFreeDelivery(int freeDelivery) {
        this.freeDelivery = freeDelivery;
    }

    public String getIncludeCancel() {
        return includeCancel;
    }

    public void setIncludeCancel(String includeCancel) {
        this.includeCancel = includeCancel == null ? null : includeCancel.trim();
    }

    public LocalDateTime getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(LocalDateTime beginDate) {
        this.beginDate = beginDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public String getBossName() {
        return bossName;
    }

    public void setBossName(String bossName) {
        this.bossName = bossName == null ? null : bossName.trim();
    }

    public String getBossId() {
        return bossId;
    }

    public void setBossId(String bossId) {
        this.bossId = bossId == null ? null : bossId.trim();
    }

    public String getBossContact() {
        return bossContact;
    }

    public void setBossContact(String bossContact) {
        this.bossContact = bossContact == null ? null : bossContact.trim();
    }

    public String getBossIdPicUp() {
        return bossIdPicUp;
    }

    public void setBossIdPicUp(String bossIdPicUp) {
        this.bossIdPicUp = bossIdPicUp == null ? null : bossIdPicUp.trim();
    }

    public String getBossIdPicBack() {
        return bossIdPicBack;
    }

    public void setBossIdPicBack(String bossIdPicBack) {
        this.bossIdPicBack = bossIdPicBack == null ? null : bossIdPicBack.trim();
    }

    public String getCfoName() {
        return cfoName;
    }

    public void setCfoName(String cfoName) {
        this.cfoName = cfoName == null ? null : cfoName.trim();
    }

    public String getCfoId() {
        return cfoId;
    }

    public void setCfoId(String cfoId) {
        this.cfoId = cfoId == null ? null : cfoId.trim();
    }

    public String getCfoContact() {
        return cfoContact;
    }

    public void setCfoContact(String cfoContact) {
        this.cfoContact = cfoContact == null ? null : cfoContact.trim();
    }

    public String getCfoIdPicUp() {
        return cfoIdPicUp;
    }

    public void setCfoIdPicUp(String cfoIdPicUp) {
        this.cfoIdPicUp = cfoIdPicUp == null ? null : cfoIdPicUp.trim();
    }

    public String getCfoIdPicBack() {
        return cfoIdPicBack;
    }

    public void setCfoIdPicBack(String cfoIdPicBack) {
        this.cfoIdPicBack = cfoIdPicBack == null ? null : cfoIdPicBack.trim();
    }
}