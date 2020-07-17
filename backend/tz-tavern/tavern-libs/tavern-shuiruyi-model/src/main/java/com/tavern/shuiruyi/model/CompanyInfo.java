package com.tavern.shuiruyi.model;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2019/08/10 <br>
 */
@TableName("company_info")
public class CompanyInfo extends Model<CompanyInfo> {

    @TableId
     String companyId;
     String companyStatus;
     String customId;
     String companyName;
     String companyType;
     String tax;
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

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCompanyStatus() {
        return companyStatus;
    }

    public void setCompanyStatus(String companyStatus) {
        this.companyStatus = companyStatus;
    }

    public String getCustomId() {
        return customId;
    }

    public void setCustomId(String customId) {
        this.customId = customId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyType() {
        return companyType;
    }

    public void setCompanyType(String companyType) {
        this.companyType = companyType;
    }

    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
        this.includeCancel = includeCancel;
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
        this.bossName = bossName;
    }

    public String getBossId() {
        return bossId;
    }

    public void setBossId(String bossId) {
        this.bossId = bossId;
    }

    public String getBossContact() {
        return bossContact;
    }

    public void setBossContact(String bossContact) {
        this.bossContact = bossContact;
    }

    public String getBossIdPicUp() {
        return bossIdPicUp;
    }

    public void setBossIdPicUp(String bossIdPicUp) {
        this.bossIdPicUp = bossIdPicUp;
    }

    public String getBossIdPicBack() {
        return bossIdPicBack;
    }

    public void setBossIdPicBack(String bossIdPicBack) {
        this.bossIdPicBack = bossIdPicBack;
    }

    public String getCfoName() {
        return cfoName;
    }

    public void setCfoName(String cfoName) {
        this.cfoName = cfoName;
    }

    public String getCfoId() {
        return cfoId;
    }

    public void setCfoId(String cfoId) {
        this.cfoId = cfoId;
    }

    public String getCfoContact() {
        return cfoContact;
    }

    public void setCfoContact(String cfoContact) {
        this.cfoContact = cfoContact;
    }

    public String getCfoIdPicUp() {
        return cfoIdPicUp;
    }

    public void setCfoIdPicUp(String cfoIdPicUp) {
        this.cfoIdPicUp = cfoIdPicUp;
    }

    public String getCfoIdPicBack() {
        return cfoIdPicBack;
    }

    public void setCfoIdPicBack(String cfoIdPicBack) {
        this.cfoIdPicBack = cfoIdPicBack;
    }
}
