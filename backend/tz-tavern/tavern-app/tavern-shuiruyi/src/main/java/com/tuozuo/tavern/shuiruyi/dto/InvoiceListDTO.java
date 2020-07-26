package com.tuozuo.tavern.shuiruyi.dto;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/7/26 <br>
 */
public class InvoiceListDTO {

    private List<InvoiceItemDTO> invoices = Lists.newArrayList();
    private int total;

    public List<InvoiceItemDTO> getInvoices() {
        return invoices;
    }

    public void setInvoices(List<InvoiceItemDTO> invoices) {
        this.invoices = invoices;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
