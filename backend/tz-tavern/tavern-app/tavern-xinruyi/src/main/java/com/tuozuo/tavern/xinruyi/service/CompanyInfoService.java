package com.tuozuo.tavern.xinruyi.service;

import com.tuozuo.tavern.xinruyi.model.CompanyInfo;
import com.tuozuo.tavern.xinruyi.model.CompanyInfoExt;
import com.tuozuo.tavern.xinruyi.vo.CompanyAuthInfoVO;

import java.util.List;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/9/4 <br>
 */
public interface CompanyInfoService {

    CompanyInfo queryCompanyInfo(String companyId);


    void applyForCompanyAuth(CompanyAuthInfoVO companyAuthInfoVO) throws Exception;

    void modifyCompanyInfo(CompanyAuthInfoVO companyAuthInfoVO) throws Exception;


    CompanyInfoExt queryCompanyDetailInfo(String companyId);

    List<CompanyInfo> queryCompanyList(String companyName, int queryCnt);


}