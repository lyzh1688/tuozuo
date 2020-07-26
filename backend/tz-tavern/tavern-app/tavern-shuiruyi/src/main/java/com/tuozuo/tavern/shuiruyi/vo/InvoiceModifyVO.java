package com.tuozuo.tavern.shuiruyi.vo;

import org.springframework.web.multipart.MultipartFile;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/7/26 <br>
 */
public class InvoiceModifyVO {
    private String invoiceId;
    private String companyName;
    private String contractName;
    private String invoiceType;
    private double invoiceAmount;
    private String invoiceStatus;
    private String recvDate;
    private String invoiceContent;
    private MultipartFile authLetterFile;
    private MultipartFile bankFlowFile;

    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
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

    public String getInvoiceStatus() {
        return invoiceStatus;
    }

    public void setInvoiceStatus(String invoiceStatus) {
        this.invoiceStatus = invoiceStatus;
    }

    public String getRecvDate() {
        return recvDate;
    }

    public void setRecvDate(String recvDate) {
        this.recvDate = recvDate;
    }

    public String getInvoiceContent() {
        return invoiceContent;
    }

    public void setInvoiceContent(String invoiceContent) {
        this.invoiceContent = invoiceContent;
    }

    public MultipartFile getAuthLetterFile() {
        return authLetterFile;
    }

    public void setAuthLetterFile(MultipartFile authLetterFile) {
        this.authLetterFile = authLetterFile;
    }

    public MultipartFile getBankFlowFile() {
        return bankFlowFile;
    }

    public void setBankFlowFile(MultipartFile bankFlowFile) {
        this.bankFlowFile = bankFlowFile;
    }
}
