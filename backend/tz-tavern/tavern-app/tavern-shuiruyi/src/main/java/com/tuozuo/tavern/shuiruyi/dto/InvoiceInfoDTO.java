package com.tuozuo.tavern.shuiruyi.dto;

import java.math.BigDecimal;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/7/26 <br>
 */
public class InvoiceInfoDTO {

    private String companyId;
    private String contractId;
    private String companyName;
    private String contractName;
    private String invoiceType;
    private double invoiceAmount;
    private double recvAmount;
    private BigDecimal tax;
    private String recvDate;
    private String authLetterFile;
    private String bankFlowFile;
    private String invoiceContent;
    private String invoiceStatus;
    private String remark;
    private String deliveryId;

    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
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

    public String getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(String deliveryId) {
        this.deliveryId = deliveryId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public String getRecvDate() {
        return recvDate;
    }

    public void setRecvDate(String recvDate) {
        this.recvDate = recvDate;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    public String getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(String invoiceType) {
        this.invoiceType = invoiceType;
    }

    public double getInvoiceAmount() {
        return invoiceAmount;
    }

    public void setInvoiceAmount(double invoiceAmount) {
        this.invoiceAmount = invoiceAmount;
    }

    public double getRecvAmount() {
        return recvAmount;
    }

    public void setRecvAmount(double recvAmount) {
        this.recvAmount = recvAmount;
    }

    public String getAuthLetterFile() {
        return authLetterFile;
    }

    public void setAuthLetterFile(String authLetterFile) {
        this.authLetterFile = authLetterFile;
    }

    public String getBankFlowFile() {
        return bankFlowFile;
    }

    public void setBankFlowFile(String bankFlowFile) {
        this.bankFlowFile = bankFlowFile;
    }

    public String getInvoiceContent() {
        return invoiceContent;
    }

    public void setInvoiceContent(String invoiceContent) {
        this.invoiceContent = invoiceContent;
    }
}
