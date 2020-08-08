package com.tuozuo.tavern.shuiruyi.dao.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tuozuo.tavern.shuiruyi.dao.CustomInfoDao;
import com.tuozuo.tavern.shuiruyi.mapper.CustomInfoMapper;
import com.tuozuo.tavern.shuiruyi.mapper.CustomStatisticSnapshotMapper;
import com.tuozuo.tavern.shuiruyi.mapper.CustomTradeFlowMapper;
import com.tuozuo.tavern.shuiruyi.model.CustomDetailInfo;
import com.tuozuo.tavern.shuiruyi.model.CustomInfo;
import com.tuozuo.tavern.shuiruyi.model.CustomTradeFlow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/7/18 <br>
 */
@Repository
public class CustomInfoDaoImpl implements CustomInfoDao {
    @Autowired
    private CustomInfoMapper customInfoMapper;
    @Autowired
    private CustomTradeFlowMapper customTradeFlowMapper;
    @Autowired
    private CustomStatisticSnapshotMapper customStatisticSnapshotMapper;


    @Override
    public CustomDetailInfo selectCustomInfo(String customId) {
        return this.customInfoMapper.selectDetailInfo(customId);
    }

    @Override
    public IPage<CustomTradeFlow> selectCustomTradeFlowList(String customId, int pageNo, int pageSize) {
        Page<CustomTradeFlow> page = new Page<>(pageNo, pageSize);
        return this.customTradeFlowMapper.selectPage(page, Wrappers.<CustomTradeFlow>query().lambda().eq(CustomTradeFlow::getCustomId, customId).orderByDesc(CustomTradeFlow::getTradeDate));
    }

    @Override
    public List<CustomInfo> fuzzyQueryCustomInfo(String customName, int queryCnt) {
        return this.customInfoMapper.selectList(Wrappers.<CustomInfo>query().lambda()
                .like(CustomInfo::getCustomName, customName)
                .orderByAsc(CustomInfo::getCustomName)
                .last("limit " + queryCnt));
    }

    @Override
    public void insert(CustomInfo customInfo) {
        this.customInfoMapper.insert(customInfo);
    }

    @Override
    public void update(CustomInfo customInfo) {
        this.customInfoMapper.updateById(customInfo);
    }

    @Override
    public IPage<CustomInfo> selectCustomInfoList(String customName, String hasPaid, int pageNo, int pageSize) {
        Page<CustomInfo> page = new Page<>(pageNo, pageSize);
        return this.customInfoMapper.selectCustomInfoList(page, customName, hasPaid);
    }

    @Override
    public void countCustomStatistic() {
        this.customStatisticSnapshotMapper.countStatistic();
    }

    @Override
    public boolean isExistUser(String userId) {
        return Optional.ofNullable(this.customInfoMapper.selectById(userId)).isPresent();
    }
}
