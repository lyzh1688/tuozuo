package com.tuozuo.tavern.xinruyi.dao;

import com.tuozuo.tavern.xinruyi.model.CompanyInfo;
import com.tuozuo.tavern.xinruyi.model.CompanyInfoExt;

import java.util.List;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/9/4 <br>
 */
public interface CompanyInfoDao {

    CompanyInfo selectCompanyInfo(String companyId);

    void insertCompanyAuthInfo(CompanyInfoExt companyInfoExt);

    void updateCompanyAuthInfo(CompanyInfoExt companyInfoExt);

    void updateCompanyInfo(CompanyInfo companyInfo);

    CompanyInfoExt selectCompanyDetailInfo(String companyId);

    List<CompanyInfo> selectCompanyList(String companyName, int queryCnt);

    void insertCompanyInfo(CompanyInfo companyInfo);

}
