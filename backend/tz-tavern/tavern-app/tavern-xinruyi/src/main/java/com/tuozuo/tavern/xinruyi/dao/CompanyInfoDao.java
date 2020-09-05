package com.tuozuo.tavern.xinruyi.dao;

import com.tuozuo.tavern.xinruyi.model.CompanyInfo;
import com.tuozuo.tavern.xinruyi.model.CompanyInfoExt;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/9/4 <br>
 */
public interface CompanyInfoDao {

    CompanyInfo selectCompanyInfo(String companyId);

    void insertCompanyAuthInfo(CompanyInfoExt companyInfoExt);

    CompanyInfoExt selectCompanyDetailInfo(String companyId);

}
