package com.tuozuo.tavern.shuiruyi.vo;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/7/21 <br>
 */
public class CompanyDetailVO {
  @NotNull(message = "companyName is not null")
  private String companyName;
  @NotNull(message = "tax is not null")
  private BigDecimal tax;
  @NotNull(message = "address is not null")
  private String address;
  @NotNull(message = "companyType is not null")
  private String companyType;

  @NotNull(message = "bossName is not null")
  private String bossName;
  @NotNull(message = "bossId is not null")
  private String bossId;
  @NotNull(message = "bossContact is not null")
  private String bossContact;
  private MultipartFile bossIdPicUp;
  private MultipartFile bossIdPicBack;

  @NotNull(message = "cfoName is not null")
  private String cfoName;
  @NotNull(message = "cfoId is not null")
  private String cfoId;
  @NotNull(message = "cfoContact is not null")
  private String cfoContact;
  private MultipartFile cfoIdPicUp;
  private MultipartFile cfoIdPicBack;
  private String customId;

  public String getCompanyName() {
    return companyName;
  }

  public void setCompanyName(String companyName) {
    this.companyName = companyName;
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
    this.address = address;
  }

  public String getCompanyType() {
    return companyType;
  }

  public void setCompanyType(String companyType) {
    this.companyType = companyType;
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

  public MultipartFile getCfoIdPicUp() {
    return cfoIdPicUp;
  }

  public void setCfoIdPicUp(MultipartFile cfoIdPicUp) {
    this.cfoIdPicUp = cfoIdPicUp;
  }

  public MultipartFile getCfoIdPicBack() {
    return cfoIdPicBack;
  }

  public void setCfoIdPicBack(MultipartFile cfoIdPicBack) {
    this.cfoIdPicBack = cfoIdPicBack;
  }

  public String getCustomId() {
    return customId;
  }

  public void setCustomId(String customId) {
    this.customId = customId;
  }

  @Override
  public String toString() {
    return "CompanyDetailVO{" +
            "companyName='" + companyName + '\'' +
            ", tax=" + tax +
            ", address='" + address + '\'' +
            ", companyType='" + companyType + '\'' +
            ", bossName='" + bossName + '\'' +
            ", bossId='" + bossId + '\'' +
            ", bossContact='" + bossContact + '\'' +
            ", bossIdPicUp=" + bossIdPicUp +
            ", bossIdPicBack=" + bossIdPicBack +
            ", cfoName='" + cfoName + '\'' +
            ", cfoId='" + cfoId + '\'' +
            ", cfoContact='" + cfoContact + '\'' +
            ", cfoIdPicUp=" + cfoIdPicUp +
            ", cfoIdPicBack=" + cfoIdPicBack +
            ", customId='" + customId + '\'' +
            '}';
  }
}
