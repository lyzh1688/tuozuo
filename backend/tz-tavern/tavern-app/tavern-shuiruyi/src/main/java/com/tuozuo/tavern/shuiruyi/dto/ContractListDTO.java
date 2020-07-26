package com.tuozuo.tavern.shuiruyi.dto;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/7/25 <br>
 */
public class ContractListDTO {
    private List<ContractItemDTO> contracts = Lists.newArrayList();
    private int total;

    public List<ContractItemDTO> getContracts() {
        return contracts;
    }

    public void setContracts(List<ContractItemDTO> contracts) {
        this.contracts = contracts;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
