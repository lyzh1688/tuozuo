package com.tuozuo.tavern.organ.biz.facade;

import com.netflix.hystrix.exception.HystrixTimeoutException;
import com.tuoyou.tavern.rpc.libs.core.hystrix.HystrixProxy;
import com.tuozuo.tavern.organ.biz.facade.qcc.CompanyBizDataService;
import com.tuozuo.tavern.organ.biz.facade.service.CompanyBizDataFactory;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CompanyBizDataServiceFallback implements FallbackFactory<CompanyBizDataService> {

    final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Override
    public CompanyBizDataService create(Throwable throwable) {
        if (throwable instanceof HystrixTimeoutException){
            LOGGER.error("CompanyBizDataService Timeout for {} ms", HystrixProxy.invokeTimeout);
            return  new CompanyBizDataFactory(-1,"CompanyBizDataService Timeout for " + HystrixProxy.invokeTimeout + " ms");
        } else {
            LOGGER.error("CompanyBizDataService Fallback caused by: ",throwable);
            return  new CompanyBizDataFactory(-1,"CompanyBizDataService Fallback caused by:" + throwable.getMessage());
        }
    }
}