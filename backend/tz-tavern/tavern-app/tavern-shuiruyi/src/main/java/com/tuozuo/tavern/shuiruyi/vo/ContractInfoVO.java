package com.tuozuo.tavern.shuiruyi.vo;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/7/25 <br>
 */
public class ContractInfoVO {
    @NotNull(message = "contractName is not null")
    private String contractName;
    @NotNull(message = "companyPartyAName is not null")
    private String companyPartyAName;
    @NotNull(message = "invoicePattern is not null")
    private String invoicePattern;
    @NotNull(message = "contractAmount is not null")
    private double contractAmount;
    private MultipartFile contractFile;

    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    public String getCompanyPartyAName() {
        return companyPartyAName;
    }

    public void setCompanyPartyAName(String companyPartyAName) {
        this.companyPartyAName = companyPartyAName;
    }

    public String getInvoicePattern() {
        return invoicePattern;
    }

    public void setInvoicePattern(String invoicePattern) {
        this.invoicePattern = invoicePattern;
    }

    public double getContractAmount() {
        return contractAmount;
    }

    public void setContractAmount(double contractAmount) {
        this.contractAmount = contractAmount;
    }

    public MultipartFile getContractFile() {
        return contractFile;
    }

    public void setContractFile(MultipartFile contractFile) {
        this.contractFile = contractFile;
    }
}
