package com.tuozuo.tavern.xinruyi.dto;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/8/30 <br>
 */
public class ProjectInfoListDTO {
    private List<ProjectInfoDTO> projects = Lists.newArrayList();
    private int total;

    public List<ProjectInfoDTO> getProjects() {
        return projects;
    }

    public void setProjects(List<ProjectInfoDTO> projects) {
        this.projects = projects;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
