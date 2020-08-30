package com.tuozuo.tavern.xinruyi.dto;

import com.google.common.collect.Lists;
import com.tuozuo.tavern.xinruyi.model.StaffSalaryInfo;

import java.util.List;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/8/30 <br>
 */
public class StaffSalaryListDTO {
    private List<StaffSalaryInfo>  payment = Lists.newArrayList();
    private int total;

    public List<StaffSalaryInfo> getPayment() {
        return payment;
    }

    public void setPayment(List<StaffSalaryInfo> payment) {
        this.payment = payment;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
