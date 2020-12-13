package com.tuozuo.tavern.organ.biz.facade.service.impl;

import com.google.common.collect.Maps;
import com.tuozuo.tavern.organ.biz.dict.DataType;
import com.tuozuo.tavern.organ.biz.facade.qcc.CompanyBizDataService;
import com.tuozuo.tavern.organ.biz.facade.qcc.model.CompanyBizData;
import com.tuozuo.tavern.organ.biz.facade.qcc.model.CompanyBizResult;
import com.tuozuo.tavern.organ.biz.facade.qcc.model.CompanyStatus;
import com.tuozuo.tavern.organ.biz.facade.service.QccCompanyDataService;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/11/24 <br>
 */
@Service
public class QccCompanyDataServiceImpl implements QccCompanyDataService {

    private static final Logger LOGGER = LoggerFactory.getLogger(QccCompanyDataServiceImpl.class);

    @Value("${qcc.key:2fd58c707a164dbdaaf9ba9d71a30022}")
    private String key;
    @Value("${qcc.secretKey:36D8F15080D2D68F213DFD64108B61A1}")
    private String secretKey;

    @Autowired
    private CompanyBizDataService companyBizDataService;


    @Override
    public CompanyBizResult queryCompanyData(String provinceCode, String cityCode, String pinyin, int pageNo, int pageSize) {
        Map<String, String> reqHeader = Maps.newConcurrentMap();
        String[] autherHeader = this.randomAuthentHeader();
        reqHeader.put("Token", autherHeader[0]);
        reqHeader.put("Timespan", autherHeader[1]);
        CompanyBizResult companyBizResult = this.companyBizDataService.getCompanyBizData(reqHeader, key, pinyin, provinceCode, cityCode, pageSize, pageNo, DataType.json.name());
        if (companyBizResult != null && companyBizResult.getBizData() != null && companyBizResult.getBizData().size() != 0) {
            List<CompanyBizData> bizData = companyBizResult.getBizData().stream()
                    .filter(data -> CompanyStatus.getOperatingStatus(data.getStatus()))
                    .collect(Collectors.toList());
            companyBizResult.setBizData(bizData);
        }
        return companyBizResult;
    }

    @Override
    public CompanyBizResult queryCompanyData(String pinyin, int pageNo, int pageSize) {
        Map<String, String> reqHeader = Maps.newConcurrentMap();
        String[] autherHeader = this.randomAuthentHeader();
        reqHeader.put("Token", autherHeader[0]);
        reqHeader.put("Timespan", autherHeader[1]);
        CompanyBizResult companyBizResult = this.companyBizDataService.getCompanyBizData(reqHeader, key, pinyin, pageSize, pageNo, DataType.json.name());

        return companyBizResult;
    }

    // 获取Auth Code
    private final String[] randomAuthentHeader() {
        String timeSpan = String.valueOf(System.currentTimeMillis() / 1000);
        String[] authentHeaders = new String[]{DigestUtils.md5Hex(key.concat(timeSpan).concat(secretKey)).toUpperCase(), timeSpan};
        return authentHeaders;
    }


}

