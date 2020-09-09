package com.tuozuo.tavern.xinruyi.dto;

import com.google.common.collect.Lists;
import com.tuozuo.tavern.xinruyi.model.ProjectEventInfo;

import java.util.List;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/9/9 <br>
 */
public class ProjectEventListDTO {
    private int total;
    private List<ProjectEventInfo> projects = Lists.newArrayList();

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<ProjectEventInfo> getProjects() {
        return projects;
    }

    public void setProjects(List<ProjectEventInfo> projects) {
        this.projects = projects;
    }
}
