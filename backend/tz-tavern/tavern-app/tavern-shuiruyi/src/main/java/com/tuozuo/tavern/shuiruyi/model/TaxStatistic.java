package com.tuozuo.tavern.shuiruyi.model;

import java.math.BigDecimal;

public class TaxStatistic {
    private String customName;

    private String companyName;

    private BigDecimal incomeTax;

    private BigDecimal valueAddedTax;

    private BigDecimal totalIncomeTax;

    private BigDecimal totalValueAddedTax;

    public BigDecimal getTotalIncomeTax() {
        return totalIncomeTax;
    }

    public void setTotalIncomeTax(BigDecimal totalIncomeTax) {
        this.totalIncomeTax = totalIncomeTax;
    }

    public BigDecimal getTotalValueAddedTax() {
        return totalValueAddedTax;
    }

    public void setTotalValueAddedTax(BigDecimal totalValueAddedTax) {
        this.totalValueAddedTax = totalValueAddedTax;
    }

    public String getCustomName() {
        return customName;
    }

    public void setCustomName(String customName) {
        this.customName = customName == null ? null : customName.trim();
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName == null ? null : companyName.trim();
    }

    public BigDecimal getIncomeTax() {
        return incomeTax;
    }

    public void setIncomeTax(BigDecimal incomeTax) {
        this.incomeTax = incomeTax;
    }

    public BigDecimal getValueAddedTax() {
        return valueAddedTax;
    }

    public void setValueAddedTax(BigDecimal valueAddedTax) {
        this.valueAddedTax = valueAddedTax;
    }
}