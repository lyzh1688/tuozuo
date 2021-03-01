package com.tuozuo.tavern.corp.assist.dao.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tuozuo.tavern.corp.assist.dao.CorporationInfoDao;
import com.tuozuo.tavern.corp.assist.mapper.CorporationInfoMapper;
import com.tuozuo.tavern.corp.assist.model.CorporationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2021/1/5 <br>
 */
@Repository
public class CorporationInfoDaoImpl extends ServiceImpl<CorporationInfoMapper, CorporationInfo> implements CorporationInfoDao {
    @Autowired
    private CorporationInfoMapper corporationInfoMapper;

    @Override
    public boolean insertCorporation(CorporationInfo corporationInfo) {
        return this.save(corporationInfo);
    }

    @Override
    public boolean delCorporation(CorporationInfo corporationInfo) {
        return this.updateById(corporationInfo);
    }

    @Override
    public boolean updateCorporation(CorporationInfo corporationInfo) {
        return this.updateById(corporationInfo);
    }

    @Override
    public IPage<CorporationInfo> selectCorporations(String corpName, String clientName, Page<CorporationInfo> page) {

        return this.corporationInfoMapper.selectByPage(page, corpName, clientName);
    }

    @Override
    public CorporationInfo selectCorporationDetail(String corpId) {
        return this.getById(corpId);
    }

    @Override
    public List<CorporationInfo> selectCorporationsFromApp(String corpName, String clientName, String corpId, String createTime) {
        return this.corporationInfoMapper.selectFromApp(corpName, clientName, corpId, createTime);
    }

    @Override
    public List<CorporationInfo> fuzzyQuery(String corpName, int queryCnt) {
        return this.list(Wrappers.<CorporationInfo>query()
                .lambda()
                .like(StringUtils.isNotEmpty(corpName), CorporationInfo::getCorpName, corpName)
                .orderByDesc(CorporationInfo::getCreateTime)
                .last("limit " + queryCnt));
    }
}
