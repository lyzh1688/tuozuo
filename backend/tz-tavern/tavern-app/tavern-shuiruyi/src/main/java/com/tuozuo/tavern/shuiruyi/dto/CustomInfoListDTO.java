package com.tuozuo.tavern.shuiruyi.dto;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/7/19 <br>
 */
public class CustomInfoListDTO {

    private List<CustomInfoDTO> customers = Lists.newArrayList();
    private int total;

    public List<CustomInfoDTO> getCustomers() {
        return customers;
    }

    public void setCustomers(List<CustomInfoDTO> customers) {
        this.customers = customers;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
