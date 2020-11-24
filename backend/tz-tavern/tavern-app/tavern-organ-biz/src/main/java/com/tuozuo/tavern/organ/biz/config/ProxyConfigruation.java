package com.tuozuo.tavern.organ.biz.config;

import com.tuoyou.tavern.rpc.libs.core.hystrix.HystrixParam;
import com.tuoyou.tavern.rpc.libs.core.hystrix.HystrixProxy;
import com.tuozuo.tavern.organ.biz.facade.service.CompanyBizDataServiceFallback;
import com.tuozuo.tavern.organ.biz.facade.qcc.CompanyBizDataService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/11/23 <br>
 */
@Configuration
public class ProxyConfigruation {

    @Value("${qcc.company.data.http.url:http://api.qichacha.com}")
    private String snapshotUrl;

    @Bean(name = "CompanyBizDataService")
    public CompanyBizDataService companyBizDataService() {
        HystrixParam hystrixParam = new HystrixParam();
        hystrixParam.setThreadPoolCoreSize(100);
        hystrixParam.setMaxQueueSizePerThread(-1);
        hystrixParam.setQueueSizeRejectionThresholdPerThread(100);
        return new HystrixProxy<CompanyBizDataService>().newProxy("CompanyBizData", CompanyBizDataService.class, new CompanyBizDataServiceFallback(), hystrixParam);
    }

}
