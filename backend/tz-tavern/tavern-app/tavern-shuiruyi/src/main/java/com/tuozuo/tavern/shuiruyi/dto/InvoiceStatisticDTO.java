package com.tuozuo.tavern.shuiruyi.dto;

import com.google.common.collect.Lists;
import com.tuozuo.tavern.shuiruyi.model.InvoiceStatistic;

import java.util.List;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/7/18 <br>
 */
public class InvoiceStatisticDTO {

    private List<InvoiceStatistic> statistics = Lists.newArrayList();
    private int total;


    public List<InvoiceStatistic> getStatistics() {
        return statistics;
    }

    public void setStatistics(List<InvoiceStatistic> statistics) {
        this.statistics = statistics;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
