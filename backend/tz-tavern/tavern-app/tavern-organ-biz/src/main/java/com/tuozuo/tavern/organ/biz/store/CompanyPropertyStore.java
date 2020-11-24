package com.tuozuo.tavern.organ.biz.store;

import com.google.common.collect.Maps;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/11/24 <br>
 */
@Component
public class CompanyPropertyStore {
    private final Map<String, String> areaMap = Maps.newConcurrentMap();
    private final Map<String, String> industryMap = Maps.newConcurrentMap();
    private final Map<String, String> typeMap = Maps.newConcurrentMap();


    public Map<String, String> getAreaMap() {
        return this.areaMap;
    }

    public Map<String, String> getIndustryMap() {
        return this.industryMap;
    }

    public Map<String, String> getTypeMap() {
        return this.typeMap;
    }

    public void updateAreaMap(String area) {
        this.areaMap.putIfAbsent(area, area);
    }

    public void updateIndustryMap(String industry) {
        this.areaMap.putIfAbsent(industry, industry);
    }

    public void updateTypeMap(String type) {
        this.areaMap.putIfAbsent(type, type);
    }


}
