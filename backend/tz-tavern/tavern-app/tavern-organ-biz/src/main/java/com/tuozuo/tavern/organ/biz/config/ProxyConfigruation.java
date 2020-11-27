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
    private String qccUrl;

    @Bean(name = "CompanyBizDataService")
    public CompanyBizDataService companyBizDataService() {
        return new HystrixProxy<CompanyBizDataService>().newProxy( CompanyBizDataService.class, qccUrl,new CompanyBizDataServiceFallback());
    }

}
