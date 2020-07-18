package com.tuozuo.tavern.shuiruyi.service;

import com.tuozuo.tavern.shuiruyi.model.CompanyDetailInfo;
import com.tuozuo.tavern.shuiruyi.model.CompanyInfo;

import java.util.List;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2019/08/10 <br>
 */
public interface CompanyInfoService {

    List<CompanyInfo> fuzzyQueryCompany(String companyName, int queryCnt, boolean isAll, String userId, String roleGroup);

    CompanyDetailInfo queryCompanyDetail(String companyId);

}
