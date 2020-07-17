package com.tuozuo.tavern.shuiruyi.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tavern.shuiruyi.model.CompanyInfo;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2019/08/10 <br>
 */
public interface CompanyInfoService extends IService<CompanyInfo> {

    List<CompanyInfo> fuzzyQueryCompany(String companyName, int queryCnt, boolean showAll);

    CompanyInfo queryCompanyDetail(String companyId);

}
