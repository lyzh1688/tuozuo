package com.tuozuo.tavern.organ.biz.facade.service;

import com.tuozuo.tavern.organ.biz.facade.qcc.model.CompanyBizResult;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/11/24 <br>
 */
public interface QccCompanyDataService {

    CompanyBizResult queryCompanyData(String provinceCode, String cityCode, String pinyin, int pageNo, int pageSize);

    CompanyBizResult queryCompanyData(String pinyin, int pageNo, int pageSize);


}
