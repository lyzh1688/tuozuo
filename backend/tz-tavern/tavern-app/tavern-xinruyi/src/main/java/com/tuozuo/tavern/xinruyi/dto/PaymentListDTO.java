package com.tuozuo.tavern.xinruyi.dto;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/9/19 <br>
 */
public class PaymentListDTO {
    private List<PaymentDTO> funds = Lists.newArrayList();
    private long total;

    public List<PaymentDTO> getFunds() {
        return funds;
    }

    public void setFunds(List<PaymentDTO> funds) {
        this.funds = funds;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }
}
