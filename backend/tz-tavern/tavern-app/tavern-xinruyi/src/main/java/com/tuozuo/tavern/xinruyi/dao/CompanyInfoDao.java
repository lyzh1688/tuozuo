package com.tuozuo.tavern.xinruyi.dao;

import com.tuozuo.tavern.xinruyi.model.CompanyInfo;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/9/4 <br>
 */
public interface CompanyInfoDao {

    CompanyInfo selectCompanyInfo(String companyId);

}
