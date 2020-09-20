package com.tuozuo.tavern.xinruyi.dto;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/9/20 <br>
 */
public class PaymentHistoryListDTO {
    private List<PaymentHistoryDTO> salaries = Lists.newArrayList();
    private long total;

    public List<PaymentHistoryDTO> getSalaries() {
        return salaries;
    }

    public void setSalaries(List<PaymentHistoryDTO> salaries) {
        this.salaries = salaries;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }
}
