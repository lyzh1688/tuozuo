package com.tuozuo.tavern.shuiruyi.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.tuozuo.tavern.common.protocol.UserTypeDict;
import com.tuozuo.tavern.shuiruyi.dao.CompanyInfoDao;
import com.tuozuo.tavern.shuiruyi.model.CompanyDetailInfo;
import com.tuozuo.tavern.shuiruyi.model.CompanyInfo;
import com.tuozuo.tavern.shuiruyi.service.CompanyInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2019/08/10 <br>
 */
@Service
public class CompanyInfoServiceImpl implements CompanyInfoService {

    @Autowired
    private CompanyInfoDao companyInfoDao;

    @Override
    public List<CompanyInfo> fuzzyQueryCompany(String companyName, int queryCnt, boolean isAll, String userId, String roleGroup) {
        if (roleGroup.equals(UserTypeDict.custom)) {

            if (isAll) {
                return this.companyInfoDao.selectAllCustomCompanyList(companyName, userId);
            } else {
                return this.companyInfoDao.selectCustomCompanyList(companyName, queryCnt, userId);
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
}
