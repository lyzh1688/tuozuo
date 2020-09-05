package com.tuozuo.tavern.xinruyi.service;

import com.tuozuo.tavern.xinruyi.model.CompanyInfo;
import com.tuozuo.tavern.xinruyi.model.CompanyInfoExt;
import com.tuozuo.tavern.xinruyi.vo.CompanyAuthInfoVO;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/9/4 <br>
 */
public interface CompanyInfoService {

    CompanyInfo queryCompanyInfo(String companyId);


    void applyForCompanyAuth(CompanyAuthInfoVO companyAuthInfoVO) throws Exception;


    CompanyInfoExt queryCompanyDetailInfo(String companyId);


}