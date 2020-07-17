package com.tavern.shuiruyi.model;

import java.math.BigDecimal;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2019/08/10 <br>
 */
public class CompanyDetailInfo extends CompanyInfo {

    private int totalInvoiceNum;
    private int invoicedNum;
    private BigDecimal totalInvoiceAmt;

    public BigDecimal getTotalInvoiceAmt() {
        return totalInvoiceAmt;
    }

    public void setTotalInvoiceAmt(BigDecimal totalInvoiceAmt) {
        this.totalInvoiceAmt = totalInvoiceAmt;
    }

    public int getTotalInvoiceNum() {
        return totalInvoiceNum;
    }

    public void setTotalInvoiceNum(int totalInvoiceNum) {
        this.totalInvoiceNum = totalInvoiceNum;
    }

    public int getInvoicedNum() {
        return invoicedNum;
    }

    public void setInvoicedNum(int invoicedNum) {
        this.invoicedNum = invoicedNum;
    }
}
