package com.tuozuo.tavern.shuiruyi.dto;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/7/19 <br>
 */
public class CustomTradeFlowListDTO {
    private List<CustomTradeFlowDTO> tradeflow = Lists.newArrayList();
    private int total;

    public List<CustomTradeFlowDTO> getTradeflow() {
        return tradeflow;
    }

    public void setTradeflow(List<CustomTradeFlowDTO> tradeflow) {
        this.tradeflow = tradeflow;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
