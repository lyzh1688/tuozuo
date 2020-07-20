package com.tuozuo.tavern.shuiruyi.dao.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tuozuo.tavern.shuiruyi.dao.CompanyInfoDao;
import com.tuozuo.tavern.shuiruyi.mapper.CompanyInfoMapper;
import com.tuozuo.tavern.shuiruyi.model.CompanyDetailInfo;
import com.tuozuo.tavern.shuiruyi.model.CompanyInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/7/18 <br>
 */
@Repository
public class CompanyInfoDaoImpl implements CompanyInfoDao {

    @Autowired
    private CompanyInfoMapper companyInfoMapper;

    @Override
    public List<CompanyInfo> selectCompanyList(String companyName, int queryCnt) {
        return this.companyInfoMapper.selectList(Wrappers.<CompanyInfo>query()
                .lambda()
                .like(CompanyInfo::getCompanyName, companyName)
                .orderByAsc(CompanyInfo::getCompanyName)
                .last("limit " + queryCnt));
    }

    @Override
    public List<CompanyInfo> selectAllCompanyList(String companyName) {
        return this.companyInfoMapper.selectList(Wrappers.<CompanyInfo>query()
                .lambda()
                .like(CompanyInfo::getCompanyName, companyName)
                .orderByAsc(CompanyInfo::getCompanyName));
    }

    @Override
    public List<CompanyInfo> selectAllCustomCompanyList(String companyName, String customId) {
        return this.companyInfoMapper.selectList(Wrappers.<CompanyInfo>query()
                .lambda()
                .like(CompanyInfo::getCompanyName, companyName)
                .eq(CompanyInfo::getCustomId, customId)
                .orderByAsc(CompanyInfo::getCompanyName));
    }

    @Override
    public List<CompanyInfo> selectCustomCompanyList(String companyName, int queryCnt, String customId) {
        return this.companyInfoMapper.selectList(Wrappers.<CompanyInfo>query()
                .lambda()
                .like(CompanyInfo::getCompanyName, companyName)
                .eq(CompanyInfo::getCustomId, customId)
                .orderByAsc(CompanyInfo::getCompanyName)
                .last("limit " + queryCnt));
    }

    @Override
    public Optional<CompanyDetailInfo> selectDetailInfo(String companyId) {
        return Optional.ofNullable(this.companyInfoMapper.selectDetailInfo(companyId));
    }

    @Override
    public IPage<CompanyInfo> selectCompanyList(String customId, String companyStatus, String registerStatus, int pageNo, int pageSize) {
        Page<CompanyInfo> page = new Page<>(pageNo, pageSize);
        return this.companyInfoMapper.selectCompanyList(page, customId, companyStatus, registerStatus);
    }
}
