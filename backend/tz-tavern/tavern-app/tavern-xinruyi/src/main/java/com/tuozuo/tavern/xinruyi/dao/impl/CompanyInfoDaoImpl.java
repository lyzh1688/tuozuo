package com.tuozuo.tavern.xinruyi.dao.impl;

import com.tuozuo.tavern.xinruyi.dao.CompanyInfoDao;
import com.tuozuo.tavern.xinruyi.mapper.CompanyInfoMapper;
import com.tuozuo.tavern.xinruyi.model.CompanyInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/9/4 <br>
 */
@Repository
public class CompanyInfoDaoImpl implements CompanyInfoDao {
    @Autowired
    private CompanyInfoMapper companyInfoMapper;


    @Override
    public CompanyInfo selectCompanyInfo(String companyId) {
        return this.companyInfoMapper.select(companyId);
    }
}
