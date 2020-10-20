package com.tuozuo.tavern.xinruyi.dao.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.tuozuo.tavern.xinruyi.dao.CompanyInfoDao;
import com.tuozuo.tavern.xinruyi.mapper.CompanyInfoExtMapper;
import com.tuozuo.tavern.xinruyi.mapper.CompanyInfoMapper;
import com.tuozuo.tavern.xinruyi.model.CompanyInfo;
import com.tuozuo.tavern.xinruyi.model.CompanyInfoExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/9/4 <br>
 */
@Repository
public class CompanyInfoDaoImpl implements CompanyInfoDao {
    @Autowired
    private CompanyInfoMapper companyInfoMapper;
    @Autowired
    private CompanyInfoExtMapper companyInfoExtMapper;


    @Override
    public CompanyInfo selectCompanyInfo(String companyId) {
        return this.companyInfoMapper.select(companyId);
    }

    @Override
    public CompanyInfo selectCompanyInfoById(String registerId) {
        return this.companyInfoMapper.selectById(registerId);
    }

    @Override
    public void insertCompanyAuthInfo(CompanyInfoExt companyInfoExt) {
        this.companyInfoExtMapper.insertOrUpdate(companyInfoExt);
    }

    @Override
    public void updateCompanyAuthInfo(CompanyInfoExt companyInfoExt) {
        this.companyInfoExtMapper.update(companyInfoExt, Wrappers.<CompanyInfoExt>query()
                .lambda()
                .eq(CompanyInfoExt::getCompanyId, companyInfoExt.getCompanyId()));
    }

    @Override
    public void updateByCompanyId(CompanyInfo companyInfo) {
        this.companyInfoMapper.update(companyInfo, Wrappers.<CompanyInfo>query()
                .lambda()
                .eq(CompanyInfo::getCompanyId, companyInfo.getCompanyId()));
    }

    @Override
    public void updateById(CompanyInfo companyInfo) {
        this.companyInfoMapper.updateById(companyInfo);
    }

    @Override
    public void updateCompanyInfoExt(CompanyInfoExt companyInfoExt) {
        this.companyInfoExtMapper.update(companyInfoExt, Wrappers.<CompanyInfoExt>query()
                .lambda()
                .eq(CompanyInfoExt::getCompanyId, companyInfoExt.getCompanyId()));
    }

    @Override
    public CompanyInfoExt selectCompanyDetailInfo(String companyId) {
        return this.companyInfoExtMapper.select(companyId);
    }

    @Override
    public List<CompanyInfo> selectCompanyList(String companyName, int queryCnt) {
        return this.companyInfoMapper.selectList(Wrappers.<CompanyInfo>query()
                .lambda()
                .like(companyName != null, CompanyInfo::getCompanyName, companyName)
                .last("limit " + queryCnt));
    }

    @Override
    public void insertCompanyInfo(CompanyInfo companyInfo) {
        this.companyInfoMapper.insert(companyInfo);
    }

    @Override
    public Optional<CompanyInfo> selectCompanyInfoByName(String companyName) {
        return Optional.ofNullable(this.companyInfoMapper.selectOne(Wrappers.<CompanyInfo>query()
                .lambda()
                .eq(CompanyInfo::getCompanyName, companyName)));
    }
}
