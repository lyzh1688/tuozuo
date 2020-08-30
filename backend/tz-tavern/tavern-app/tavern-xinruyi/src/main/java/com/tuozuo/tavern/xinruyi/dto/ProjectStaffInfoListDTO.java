package com.tuozuo.tavern.xinruyi.dto;

import com.google.common.collect.Lists;
import com.tuozuo.tavern.xinruyi.model.ProjectStaffInfo;

import java.util.List;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/8/30 <br>
 */
public class ProjectStaffInfoListDTO {
    private List<ProjectStaffInfo> staffs = Lists.newArrayList();
    private int total;

    public List<ProjectStaffInfo> getStaffs() {
        return staffs;
    }

    public void setStaffs(List<ProjectStaffInfo> staffs) {
        this.staffs = staffs;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
