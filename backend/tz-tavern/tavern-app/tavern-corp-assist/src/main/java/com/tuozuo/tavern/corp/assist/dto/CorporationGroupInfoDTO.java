package com.tuozuo.tavern.corp.assist.dto;

import com.google.common.collect.Lists;
import com.tuozuo.tavern.corp.assist.model.CorporationClientTagInfo;
import com.tuozuo.tavern.corp.assist.model.CorporationGroupClientInfo;

import java.util.List;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2021/1/2 <br>
 */
public class CorporationGroupInfoDTO {
    private List<CorporationGroupClientInfo> groups = Lists.newArrayList();
    private int total;

    public List<CorporationGroupClientInfo> getGroups() {
        return groups;
    }

    public void setGroups(List<CorporationGroupClientInfo> groups) {
        this.groups = groups;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
