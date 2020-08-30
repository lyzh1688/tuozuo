package com.tuozuo.tavern.xinruyi.dto;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/8/30 <br>
 */
public class StaffResourcePoolListDTO {
    private List<StaffResourcePoolDTO> staffs = Lists.newArrayList();
    private int total;

    public List<StaffResourcePoolDTO> getStaffs() {
        return staffs;
    }

    public void setStaffs(List<StaffResourcePoolDTO> staffs) {
        this.staffs = staffs;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
