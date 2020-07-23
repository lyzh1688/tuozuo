package com.tuozuo.tavern.shuiruyi.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tuozuo.tavern.shuiruyi.model.CustomDetailInfo;
import com.tuozuo.tavern.shuiruyi.model.CustomInfo;
import com.tuozuo.tavern.shuiruyi.model.CustomTradeFlow;

import java.util.List;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/7/18 <br>
 */
public interface CustomInfoDao {

    CustomDetailInfo selectCustomInfo(String customId);

    IPage<CustomTradeFlow> selectCustomTradeFlowList(String customId, int pageNo, int pageSize);

    List<CustomInfo> fuzzyQueryCustomInfo(String customName, int queryCnt);

    void insert(CustomInfo customInfo);

    void update(CustomInfo customInfo);

    IPage<CustomInfo> selectCustomInfoList(String customName, String hasPaid, int pageNo, int pageSize);

    void countCustomStatistic();
}
