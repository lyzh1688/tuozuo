package com.tuozuo.tavern.xinruyi.dto;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/10/11 <br>
 */
public class IndustryTypeListDTO {
    private List<IndustryTypeDTO> industries = Lists.newArrayList();

    public List<IndustryTypeDTO> getIndustries() {
        return industries;
    }

    public void setIndustries(List<IndustryTypeDTO> industries) {
        this.industries = industries;
    }
}
