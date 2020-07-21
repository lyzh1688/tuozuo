package com.tuozuo.tavern.shuiruyi.vo;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2019/08/10 <br>
 */
public class CompanyInfoVO {
    @NotNull(message = "companyName is not null")
    private String companyName;
    @NotNull(message = "tax is not null")
    private BigDecimal tax;
    @NotNull(message = "address is not null")
    private String address;
    @NotNull(message = "companyType is not null")
    private String companyType;

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
}

