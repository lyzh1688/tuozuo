package com.tuozuo.tavern.shuiruyi.model;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/7/18 <br>
 */
public class InvoiceStatistic {
    private String invoiceDate;
    private String companyName;
    private String companyId;
    private int invoiceCnt;

    public String getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(String invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public int getInvoiceCnt() {
        return invoiceCnt;
    }

    public void setInvoiceCnt(int invoiceCnt) {
        this.invoiceCnt = invoiceCnt;
    }
}
