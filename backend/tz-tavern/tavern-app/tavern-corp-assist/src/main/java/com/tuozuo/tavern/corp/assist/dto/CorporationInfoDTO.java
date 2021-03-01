package com.tuozuo.tavern.corp.assist.dto;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2021/1/5 <br>
 */
public class CorporationInfoDTO {
    private List<CorporationDTO> corps = Lists.newArrayList();
    private int total;

    public List<CorporationDTO> getCorps() {
        return corps;
    }

    public void setCorps(List<CorporationDTO> corps) {
        this.corps = corps;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
