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
    public void insertCompanyAuthInfo(CompanyInfoExt companyInfoExt) {
        this.companyInfoExtMapper.insertOrUpdate(companyInfoExt);
    }

    @Override
    public void updateCompanyAuthInfo(CompanyInfoExt companyInfoExt) {
        this.companyInfoExtMapper.updateById(companyInfoExt);
    }

    @Override
    public CompanyInfoExt selectCompanyDetailInfo(String companyId) {
        return this.companyInfoExtMapper.select(companyId);
    }

    @Override
    public List<CompanyInfo> selectCompanyList(String companyName, int queryCnt) {
        return this.companyInfoMapper.selectList(Wrappers.<CompanyInfo>query()
                .lambda()
                .like(companyName != null,CompanyInfo::getCompanyName, companyName)
                .last("limit " + queryCnt));
    }
}