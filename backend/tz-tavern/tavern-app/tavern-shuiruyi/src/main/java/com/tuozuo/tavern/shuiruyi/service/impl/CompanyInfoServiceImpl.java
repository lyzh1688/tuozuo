package com.tuozuo.tavern.shuiruyi.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tuozuo.tavern.common.protocol.UserTypeDict;
import com.tuozuo.tavern.shuiruyi.dao.CompanyInfoDao;
import com.tuozuo.tavern.shuiruyi.model.CompanyDetailInfo;
import com.tuozuo.tavern.shuiruyi.model.CompanyInfo;
import com.tuozuo.tavern.shuiruyi.service.CompanyInfoService;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2019/08/10 <br>
 */
@Service
public class CompanyInfoServiceImpl implements CompanyInfoService {

    @Autowired
    private CompanyInfoDao companyInfoDao;

    @Override
    public List<CompanyInfo> fuzzyQueryCompany(String companyName, int queryCnt, boolean isAll, String customId, String roleGroup) {
        if (roleGroup.equals(UserTypeDict.custom)) {

            if (isAll) {
                return this.companyInfoDao.selectAllCustomCompanyList(companyName, customId);
            } else {
                return this.companyInfoDao.selectCustomCompanyList(companyName, queryCnt, customId);
            }

        } else {
            if (isAll) {
                return this.companyInfoDao.selectAllCompanyList(companyName);
            } else {
                return this.companyInfoDao.selectCompanyList(companyName, queryCnt);
            }

        }

    }

    @Override
    public CompanyDetailInfo queryCompanyDetail(String companyId) {
        return this.companyInfoDao.selectDetailInfo(companyId).isPresent() ? this.companyInfoDao.selectDetailInfo(companyId).get() : null;
    }

    @Override
    public IPage<CompanyInfo> queryCompanyList(String customId, String roleGroup, String companyStatus, String registerStatus, int pageNo, int pageSize) {
        if (roleGroup.equals(UserTypeDict.custom)) {
            return this.companyInfoDao.selectCompanyList(customId, companyStatus, registerStatus, pageNo, pageSize);
        } else {
            return this.companyInfoDao.selectCompanyList(null, companyStatus, registerStatus, pageNo, pageSize);
        }
    }
}
