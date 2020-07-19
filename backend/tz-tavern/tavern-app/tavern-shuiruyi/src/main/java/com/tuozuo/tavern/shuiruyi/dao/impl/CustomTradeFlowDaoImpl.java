package com.tuozuo.tavern.shuiruyi.dao.impl;

import com.tuozuo.tavern.shuiruyi.dao.CustomTradeFlowDao;
import com.tuozuo.tavern.shuiruyi.mapper.CustomTradeFlowMapper;
import com.tuozuo.tavern.shuiruyi.model.CustomTradeFlow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/7/20 <br>
 */
@Repository
public class CustomTradeFlowDaoImpl implements CustomTradeFlowDao {
    @Autowired
    private CustomTradeFlowMapper customTradeFlowMapper;

    @Override
    public void insert(CustomTradeFlow customTradeFlow) {
        this.customTradeFlowMapper.insert(customTradeFlow);
    }
}
