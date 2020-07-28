package com.tuozuo.tavern.shuiruyi.model;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/7/28 <br>
 */
public class InvoiceDetailInfo extends InvoiceInfo {

   private String companyPartyAName;
   private String companyPartyBName;
   private String contractName;
   private String companyName;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
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
}
