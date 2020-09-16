package com.tuozuo.tavern.xinruyi.dao;

import com.tuozuo.tavern.xinruyi.model.CompanyInfo;
import com.tuozuo.tavern.xinruyi.model.CompanyInfoExt;

import java.util.List;
import java.util.Optional;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/9/4 <br>
 */
public interface CompanyInfoDao {

    CompanyInfo selectCompanyInfo(String companyId);

    CompanyInfo selectCompanyInfoById(String registerId);

    void insertCompanyAuthInfo(CompanyInfoExt companyInfoExt);

    void updateCompanyAuthInfo(CompanyInfoExt companyInfoExt);

    void updateByCompanyId(CompanyInfo companyInfo);

    void updateById(CompanyInfo companyInfo);

    void updateCompanyInfoExt(CompanyInfoExt companyInfoExt);

    CompanyInfoExt selectCompanyDetailInfo(String companyId);

    List<CompanyInfo> selectCompanyList(String companyName, int queryCnt);

    void insertCompanyInfo(CompanyInfo companyInfo);

    Optional<CompanyInfo> selectCompanyInfoByName(String companyName);

}
