package com.tuozuo.tavern.shuiruyi.dto;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/7/26 <br>
 */
public class InvoiceInfoDTO {

   private String companyName;
   private String contractName;
   private String invoiceType;
   private double invoiceAmount;
   private double recvAmount;
   private String authLetterFile;
   private String bankFlowFile;
   private String invoiceContent;

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
