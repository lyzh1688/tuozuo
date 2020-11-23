package com.tuozuo.tavern.organ.biz.facade.qcc;

import com.tuozuo.tavern.organ.biz.facade.qcc.model.CompanyBizResult;
import feign.HeaderMap;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

import java.util.Map;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/11/23 <br>
 */
@Headers({"Content-Type: application/json,text/plain,text/html", "Accept: application/json,text/plain,text/html"})
public interface CompanyBizDataService {

    @RequestLine("GET http://api.qichacha.com/ECIV4/Search")
    CompanyBizResult getCompanyBizData(@HeaderMap Map<String, String> headerMap,
                                       @Param("key") String key,
                                       @Param("keyword") String keyword,
                                       @Param("provinceCode") String provinceCode,
                                       @Param("cityCode") String cityCode,
                                       @Param("pageIndex") String pageIndex,
                                       @Param("dtype") String dtype);
}
