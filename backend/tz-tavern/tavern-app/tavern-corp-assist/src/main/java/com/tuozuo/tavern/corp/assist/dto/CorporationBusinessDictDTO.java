package com.tuozuo.tavern.corp.assist.dto;

import com.google.common.collect.Lists;
import com.tuozuo.tavern.corp.assist.model.BusinessDict;
import com.tuozuo.tavern.corp.assist.model.CorporationTagInfo;

import java.util.List;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2021/1/2 <br>
 */
public class CorporationBusinessDictDTO {
    private List<BusinessDict> dicts = Lists.newArrayList();
    private int total;

    public List<BusinessDict> getDicts() {
        return dicts;
    }

    public void setDicts(List<BusinessDict> dicts) {
        this.dicts = dicts;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
