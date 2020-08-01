package com.tuozuo.tavern.shuiruyi.dao;

import com.tuozuo.tavern.shuiruyi.model.AreaInfo;

import java.util.List;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/8/1 <br>
 */
public interface BusinessSearchDao {

    List<AreaInfo> selectAreaInfo(String areaCode,String areaLevel);

}
