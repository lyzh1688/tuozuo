package com.tuozuo.tavern.xinruyi.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tuozuo.tavern.xinruyi.model.CompanyEventInfo;
import com.tuozuo.tavern.xinruyi.model.CompanyInfo;
import com.tuozuo.tavern.xinruyi.model.CompanyInfoExt;
import com.tuozuo.tavern.xinruyi.vo.CompanyApplyVO;
import com.tuozuo.tavern.xinruyi.vo.CompanyAuthInfoVO;
import com.tuozuo.tavern.xinruyi.vo.CompanyEventVO;

import java.util.List;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/9/4 <br>
 */
public interface CompanyInfoService {

    void companyApply(CompanyApplyVO vo);

    CompanyInfo queryCompanyInfo(String companyId);

    void applyForCompanyAuth(CompanyAuthInfoVO companyAuthInfoVO) throws Exception;

    void modifyCompanyInfo(CompanyAuthInfoVO companyAuthInfoVO) throws Exception;

    CompanyInfoExt queryCompanyDetailInfo(String companyId);

    List<CompanyInfo> queryCompanyList(String companyName, int queryCnt);

    IPage<CompanyEventInfo> queryCompanyEvents(CompanyEventVO vo);
}