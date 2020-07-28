package com.tuozuo.tavern.shuiruyi.model;

import java.math.BigDecimal;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/7/28 <br>
 */
public class ContractDetailInfo extends ContractInfo {

    private BigDecimal invoiceAmount;

    public BigDecimal getInvoiceAmount() {
        return invoiceAmount;
    }

    public void setInvoiceAmount(BigDecimal invoiceAmount) {
        this.invoiceAmount = invoiceAmount;
    }
}
