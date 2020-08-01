package com.tuozuo.tavern.shuiruyi.dto;

import com.google.common.collect.Lists;

import java.math.BigDecimal;
import java.util.List;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/8/1 <br>
 */
public class TaxStatisticListDTO {
    private List<TaxStatisticDTO> statistics = Lists.newArrayList();
    private int total;
    private BigDecimal totalIncomeTax;

    private BigDecimal totalValueAddedTax;

    public List<TaxStatisticDTO> getStatistics() {
        return statistics;
    }

    public void setStatistics(List<TaxStatisticDTO> statistics) {
        this.statistics = statistics;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

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
}
