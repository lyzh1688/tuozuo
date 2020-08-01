package com.tuozuo.tavern.shuiruyi.vo;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/7/31 <br>
 */
public class TaxStatisticVO extends PageVO {
  private String registerArea;
  private String customId;
  private String areaCode;
  private String areaLevel;
  private String invoiceType;
  private String tax;
  private String beginDate;
  private String endDate;

    public String getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(String invoiceType) {
        this.invoiceType = invoiceType;
    }

    public String getRegisterArea() {
        return registerArea;
    }

    public void setRegisterArea(String registerArea) {
        this.registerArea = registerArea;
    }

    public String getCustomId() {
        return customId;
    }

    public void setCustomId(String customId) {
        this.customId = customId;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getAreaLevel() {
        return areaLevel;
    }

    public void setAreaLevel(String areaLevel) {
        this.areaLevel = areaLevel;
    }

    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
