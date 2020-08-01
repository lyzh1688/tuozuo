package com.tuozuo.tavern.shuiruyi.dao.impl;

import com.tuozuo.tavern.shuiruyi.dao.BusinessSearchDao;
import com.tuozuo.tavern.shuiruyi.mapper.AreaInfoMapper;
import com.tuozuo.tavern.shuiruyi.model.AreaInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/8/1 <br>
 */
@Repository
public class BusinessSearchDaoImpl implements BusinessSearchDao {

    @Autowired
    private AreaInfoMapper areaInfoMapper;

    @Override
    public List<AreaInfo> selectAreaInfo(String areaCode, String areaLevel) {
        return this.areaInfoMapper.selectList(areaCode,areaLevel);
    }
}
