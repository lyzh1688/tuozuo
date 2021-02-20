package com.tuozuo.tavern.corp.assist.vo;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2021/1/5 <br>
 */
public class CorporationInfoVO {

    private String corpId;
    private String clientId;
    private String corpName;
    @NotNull(message = "corpType 不能为空")
    private String corpType;
    @NotNull(message = "taxType 不能为空")
    private String taxType;
    @NotNull(message = "registerPark 不能为空")
    private String registerPark;
    @NotNull(message = "corpStatus 不能为空")
    private String corpStatus;
    @NotNull(message = "bossName 不能为空")
    private String bossName;
    @NotNull(message = "bossNumber 不能为空")
    private String bossNumber;
    @NotNull(message = "bossGender 不能为空")
    private String bossGender;
    @NotNull(message = "bossIdNumber 不能为空")
    private String bossIdNumber;
    private String businessScope;
    @NotNull(message = "registerDate 不能为空")
    private String registerDate;
    private BigDecimal registerAsset;
    private String operatorId;
    private String operator;
    private String source;

    public String getCorpId() {
        return corpId;
    }

    public void setCorpId(String corpId) {
        this.corpId = corpId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getCorpName() {
        return corpName;
    }

    public void setCorpName(String corpName) {
        this.corpName = corpName;
    }

    public String getCorpType() {
        return corpType;
    }

    public void setCorpType(String corpType) {
        this.corpType = corpType;
    }

    public String getTaxType() {
        return taxType;
    }

    public void setTaxType(String taxType) {
        this.taxType = taxType;
    }

    public String getRegisterPark() {
        return registerPark;
    }

    public void setRegisterPark(String registerPark) {
        this.registerPark = registerPark;
    }

    public String getCorpStatus() {
        return corpStatus;
    }

    public void setCorpStatus(String corpStatus) {
        this.corpStatus = corpStatus;
    }

    public String getBossName() {
        return bossName;
    }

    public void setBossName(String bossName) {
        this.bossName = bossName;
    }

    public String getBossNumber() {
        return bossNumber;
    }

    public void setBossNumber(String bossNumber) {
        this.bossNumber = bossNumber;
    }

    public String getBossGender() {
        return bossGender;
    }

    public void setBossGender(String bossGender) {
        this.bossGender = bossGender;
    }

    public String getBossIdNumber() {
        return bossIdNumber;
    }

    public void setBossIdNumber(String bossIdNumber) {
        this.bossIdNumber = bossIdNumber;
    }

    public String getBusinessScope() {
        return businessScope;
    }

    public void setBusinessScope(String businessScope) {
        this.businessScope = businessScope;
    }

    public String getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate;
    }

    public BigDecimal getRegisterAsset() {
        return registerAsset;
    }

    public void setRegisterAsset(BigDecimal registerAsset) {
        this.registerAsset = registerAsset;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
