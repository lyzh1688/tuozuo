package com.tuozuo.tavern.shuiruyi.service.impl;

import com.tuozuo.tavern.shuiruyi.dao.BusinessSearchDao;
import com.tuozuo.tavern.shuiruyi.model.AreaInfo;
import com.tuozuo.tavern.shuiruyi.service.BusinessSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/8/1 <br>
 */
@Service
public class BusinessSearchServiceImpl implements BusinessSearchService {
    @Autowired
    private BusinessSearchDao businessSearchDao;

    @Override
    public List<AreaInfo> queryAreaInfo(String areaCode, String areaLevel) {
        return this.businessSearchDao.selectAreaInfo(areaCode, areaLevel);
    }
}
