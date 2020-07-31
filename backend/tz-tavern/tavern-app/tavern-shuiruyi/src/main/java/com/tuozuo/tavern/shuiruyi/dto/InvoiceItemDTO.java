package com.tuozuo.tavern.shuiruyi.dto;

import java.math.BigDecimal;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/7/26 <br>
 */
public class InvoiceItemDTO {
    private String invoiceId;
    private String companyPartyAName;
    private String companyPartyBName;
    private String contractName;
    private double invoiceAmount;
    private String deliveryId;
    private String invoiceStatus;
    private String remark;
    private BigDecimal tax;

    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    public String getCompanyPartyBName() {
        return companyPartyBName;
    }

    public void setCompanyPartyBName(String companyPartyBName) {
        this.companyPartyBName = companyPartyBName;
    }

    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getCompanyPartyAName() {
        return companyPartyAName;
    }

    public void setCompanyPartyAName(String companyPartyAName) {
        this.companyPartyAName = companyPartyAName;
    }

    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    public double getInvoiceAmount() {
        return invoiceAmount;
    }

    public void setInvoiceAmount(double invoiceAmount) {
        this.invoiceAmount = invoiceAmount;
    }

    public String getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(String deliveryId) {
        this.deliveryId = deliveryId;
    }

    public String getInvoiceStatus() {
        return invoiceStatus;
    }

    public void setInvoiceStatus(String invoiceStatus) {
        this.invoiceStatus = invoiceStatus;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
