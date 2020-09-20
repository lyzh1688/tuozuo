package com.tuozuo.tavern.xinruyi.dto;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/9/20 <br>
 */
public class StaffSalaryInfoListDTO {
    private List<StaffSalaryDetailDTO> staffs = Lists.newArrayList();
    private long total;

    public List<StaffSalaryDetailDTO> getStaffs() {
        return staffs;
    }

    public void setStaffs(List<StaffSalaryDetailDTO> staffs) {
        this.staffs = staffs;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }
}
