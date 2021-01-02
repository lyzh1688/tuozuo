package com.tuozuo.tavern.corp.assist.dto;

import com.google.common.collect.Lists;
import com.tuozuo.tavern.corp.assist.model.CorporationTagInfo;

import java.util.List;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2021/1/2 <br>
 */
public class CorporationTagInfoDTO {
    private List<CorporationTagInfo> tags = Lists.newArrayList();
    private int total;

    public List<CorporationTagInfo> getTags() {
        return tags;
    }

    public void setTags(List<CorporationTagInfo> tags) {
        this.tags = tags;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
