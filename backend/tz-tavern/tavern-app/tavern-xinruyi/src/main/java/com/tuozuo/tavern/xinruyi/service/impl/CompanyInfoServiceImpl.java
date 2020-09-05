package com.tuozuo.tavern.xinruyi.service.impl;

import com.tuozuo.tavern.xinruyi.dao.CompanyInfoDao;
import com.tuozuo.tavern.xinruyi.model.CompanyInfo;
import com.tuozuo.tavern.xinruyi.service.CompanyInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/9/4 <br>
 */
@Service
public class CompanyInfoServiceImpl implements CompanyInfoService {
    @Autowired
    private CompanyInfoDao companyInfoDao;


    @Override
    public CompanyInfo queryCompanyInfo(String companyId) {
        return this.companyInfoDao.selectCompanyInfo(companyId);
    }
}
