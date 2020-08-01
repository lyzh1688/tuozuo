package com.tuozuo.tavern.shuiruyi.service;

import com.tuozuo.tavern.shuiruyi.model.AreaInfo;

import java.util.List;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/8/1 <br>
 */
public interface BusinessSearchService {

    List<AreaInfo> queryAreaInfo(String areaCode,String areaLevel);


}
