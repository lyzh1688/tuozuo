package com.tuozuo.tavern.shuiruyi.model;

import java.math.BigDecimal;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/7/19 <br>
 */
public class CustomDetailInfo extends CustomInfo {
    private BigDecimal totalServerCharge;

    public BigDecimal getTotalServerCharge() {
        return totalServerCharge;
    }

    public void setTotalServerCharge(BigDecimal totalServerCharge) {
        this.totalServerCharge = totalServerCharge;
    }
}
