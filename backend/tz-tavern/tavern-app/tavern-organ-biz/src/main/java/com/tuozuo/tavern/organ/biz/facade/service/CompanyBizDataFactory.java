package com.tuozuo.tavern.organ.biz.facade.service;

import com.tuozuo.tavern.organ.biz.facade.qcc.CompanyBizDataService;
import com.tuozuo.tavern.organ.biz.facade.qcc.model.CompanyBizResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/11/23 <br>
 */

public class CompanyBizDataFactory extends BaseServiceFactory implements CompanyBizDataService {
    private Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    public CompanyBizDataFactory(int errorCode, String errorInfo) {
        super(errorCode, errorInfo);
    }


    @Override
    public CompanyBizResult getCompanyBizData(Map<String, String> headerMap, String key, String keyword, String provinceCode, String cityCode, int pageSize, int pageIndex, String dtype) {
        LOGGER.error("[企查查企业工商信息查询接口失败], keyword: {}",key);
        return null;
    }

    @Override
    public CompanyBizResult getCompanyBizData(Map<String, String> headerMap, String key, String keyword, int pageSize, int pageIndex, String dtype) {
        LOGGER.error("[企查查企业工商信息查询接口失败], keyword: {}",key);
        return null;
    }
}
