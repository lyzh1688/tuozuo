package com.tuozuo.tavern.shuiruyi.vo;

import org.springframework.web.multipart.MultipartFile;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/7/26 <br>
 */
public class ContractModifyVO {
    private String companyId;
    private String contractId;
    private String companyPartyAName;
    private String companyPartyBName;
    private String contractName;
    private double contractAmount;
    private String invoicePattern;
    private MultipartFile contractFile;
    private String contractStatus;
    private String remark;

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

    public String getCompanyPartyAName() {
        return companyPartyAName;
    }

    public void setCompanyPartyAName(String companyPartyAName) {
        this.companyPartyAName = companyPartyAName;
    }

    public String getCompanyPartyBName() {
        return companyPartyBName;
    }

    public void setCompanyPartyBName(String companyPartyBName) {
        this.companyPartyBName = companyPartyBName;
    }

    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    public double getContractAmount() {
        return contractAmount;
    }

    public void setContractAmount(double contractAmount) {
        this.contractAmount = contractAmount;
    }

    public String getInvoicePattern() {
        return invoicePattern;
    }

    public void setInvoicePattern(String invoicePattern) {
        this.invoicePattern = invoicePattern;
    }

    public MultipartFile getContractFile() {
        return contractFile;
    }

    public void setContractFile(MultipartFile contractFile) {
        this.contractFile = contractFile;
    }

    public String getContractStatus() {
        return contractStatus;
    }

    public void setContractStatus(String contractStatus) {
        this.contractStatus = contractStatus;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
