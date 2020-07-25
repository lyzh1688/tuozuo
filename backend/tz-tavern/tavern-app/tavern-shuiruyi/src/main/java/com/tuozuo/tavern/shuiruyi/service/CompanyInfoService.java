package com.tuozuo.tavern.shuiruyi.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tuozuo.tavern.shuiruyi.model.CompanyDetailInfo;
import com.tuozuo.tavern.shuiruyi.model.CompanyInfo;
import com.tuozuo.tavern.shuiruyi.vo.CompanyDetailVO;
import com.tuozuo.tavern.shuiruyi.vo.CompanyModifyVO;

import java.util.List;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2019/08/10 <br>
 */
public interface CompanyInfoService {

    List<CompanyInfo> fuzzyQueryCompany(String companyName, int queryCnt, boolean isAll, String customId, String roleGroup);

    CompanyDetailInfo queryCompanyDetail(String companyId);

    IPage<CompanyInfo> queryCompanyList(String customId, String roleGroup, String companyStatus, String registerStatus, int pageNo, int pageSize);

    void addCompanyInfo(CompanyDetailVO vo) throws Exception;

    void modifyCompanyInfo(CompanyDetailVO vo, String companyId) throws Exception;

    void modifyCompanyInfo(CompanyModifyVO vo);

}
