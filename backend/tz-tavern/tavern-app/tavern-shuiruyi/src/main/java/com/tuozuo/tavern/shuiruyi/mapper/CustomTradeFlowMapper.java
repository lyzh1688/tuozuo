package com.tuozuo.tavern.shuiruyi.mapper;

import com.tuozuo.tavern.shuiruyi.model.CustomTradeFlow;
import java.util.List;

public interface CustomTradeFlowMapper {
    int deleteByPrimaryKey(String tradeFlowId);

    int insert(CustomTradeFlow record);

    CustomTradeFlow selectByPrimaryKey(String tradeFlowId);

    List<CustomTradeFlow> selectAll();

    int updateByPrimaryKey(CustomTradeFlow record);
}