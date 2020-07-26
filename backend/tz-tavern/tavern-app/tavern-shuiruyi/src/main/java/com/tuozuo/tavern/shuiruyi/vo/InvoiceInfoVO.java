package com.tuozuo.tavern.shuiruyi.vo;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/7/26 <br>
 */
public class InvoiceInfoVO {

    @NotNull(message = "companyId is not null")
    private String companyId;
    @NotNull(message = "contractId is not null")
    private String contractId;
    @NotNull(message = "invoiceType is not null")
    private String invoiceType;
    @NotNull(message = "invoiceAmount is not null")
    private double invoiceAmount;
    @NotNull(message = "recvAmount is not null")
    private double recvAmount;
    private MultipartFile authLetterFile;
    private MultipartFile bankFlowFile;

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
