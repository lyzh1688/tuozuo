package com.tuozuo.tavern.corp.assist.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@TableName("corporation_info")
public class CorporationInfo extends Model<CorporationInfo> {
    @TableId
    private String corpId;

    private String corpName;

    private String corpType;

    private String taxType;

    @TableField(exist = false)
    private String corpTypeId;
    @TableField(exist = false)
    private String taxTypeId;

    private String registerPark;
    @TableField(exist = false)
    private String registerParkId;

    private String corpStatus;
    @TableField(exist = false)
    private String corpStatusId;

    private String bossName;

    private String bossNumber;

    private String bossGender;
    @TableField(exist = false)
    private String bossGenderId;

    private String bossIdNumber;

    private String businessScope;

    private LocalDate registerDate;

    private BigDecimal registerAsset;

    private LocalDateTime createTime;

    private String valid;

    private String operator;

    private String operatorId;

    private String source;

    public static CorporationInfo create(String corpId, String valid) {
        CorporationInfo corporationInfo = new CorporationInfo();
        corporationInfo.setCorpId(corpId);
        corporationInfo.setValid(valid);
        return corporationInfo;
    }

    public String getBossGenderId() {
        return bossGenderId;
    }

    public void setBossGenderId(String bossGenderId) {
        this.bossGenderId = bossGenderId;
    }

    public String getCorpTypeId() {
        return corpTypeId;
    }

    public void setCorpTypeId(String corpTypeId) {
        this.corpTypeId = corpTypeId;
    }

    public String getTaxTypeId() {
        return taxTypeId;
    }

    public void setTaxTypeId(String taxTypeId) {
        this.taxTypeId = taxTypeId;
    }

    public String getRegisterParkId() {
        return registerParkId;
    }

    public void setRegisterParkId(String registerParkId) {
        this.registerParkId = registerParkId;
    }

    public String getCorpStatusId() {
        return corpStatusId;
    }

    public void setCorpStatusId(String corpStatusId) {
        this.corpStatusId = corpStatusId;
    }

    public String getCorpId() {
        return corpId;
    }

    public void setCorpId(String corpId) {
        this.corpId = corpId == null ? null : corpId.trim();
    }

    public String getCorpName() {
        return corpName;
    }

    public void setCorpName(String corpName) {
        this.corpName = corpName == null ? null : corpName.trim();
    }

    public String getCorpType() {
        return corpType;
    }

    public void setCorpType(String corpType) {
        this.corpType = corpType == null ? null : corpType.trim();
    }

    public String getTaxType() {
        return taxType;
    }

    public void setTaxType(String taxType) {
        this.taxType = taxType == null ? null : taxType.trim();
    }

    public String getRegisterPark() {
        return registerPark;
    }

    public void setRegisterPark(String registerPark) {
        this.registerPark = registerPark == null ? null : registerPark.trim();
    }

    public String getCorpStatus() {
        return corpStatus;
    }

    public void setCorpStatus(String corpStatus) {
        this.corpStatus = corpStatus == null ? null : corpStatus.trim();
    }

    public String getBossName() {
        return bossName;
    }

    public void setBossName(String bossName) {
        this.bossName = bossName == null ? null : bossName.trim();
    }

    public String getBossNumber() {
        return bossNumber;
    }

    public void setBossNumber(String bossNumber) {
        this.bossNumber = bossNumber == null ? null : bossNumber.trim();
    }

    public String getBossGender() {
        return bossGender;
    }

    public void setBossGender(String bossGender) {
        this.bossGender = bossGender == null ? null : bossGender.trim();
    }

    public String getBossIdNumber() {
        return bossIdNumber;
    }

    public void setBossIdNumber(String bossIdNumber) {
        this.bossIdNumber = bossIdNumber == null ? null : bossIdNumber.trim();
    }

    public String getBusinessScope() {
        return businessScope;
    }

    public void setBusinessScope(String businessScope) {
        this.businessScope = businessScope == null ? null : businessScope.trim();
    }

    public LocalDate getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(LocalDate registerDate) {
        this.registerDate = registerDate;
    }

    public BigDecimal getRegisterAsset() {
        return registerAsset;
    }

    public void setRegisterAsset(BigDecimal registerAsset) {
        this.registerAsset = registerAsset;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public String getValid() {
        return valid;
    }

    public void setValid(String valid) {
        this.valid = valid == null ? null : valid.trim();
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator == null ? null : operator.trim();
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId == null ? null : operatorId.trim();
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source == null ? null : source.trim();
    }
}