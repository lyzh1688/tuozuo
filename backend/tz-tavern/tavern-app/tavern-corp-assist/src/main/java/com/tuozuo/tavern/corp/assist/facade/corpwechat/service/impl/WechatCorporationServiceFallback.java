package com.tuozuo.tavern.corp.assist.facade.corpwechat.service.impl;

import com.netflix.hystrix.exception.HystrixTimeoutException;
import com.tuoyou.tavern.rpc.libs.core.hystrix.HystrixProxy;
import com.tuozuo.tavern.corp.assist.facade.corpwechat.service.WechatCorporationService;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class WechatCorporationServiceFallback implements FallbackFactory<WechatCorporationService> {

    private static final Logger LOGGER = LoggerFactory.getLogger(WechatCorporationServiceFallback.class);

    @Override
    public WechatCorporationService create(Throwable throwable) {
        if (throwable instanceof HystrixTimeoutException){
            LOGGER.error("WechatCorporationService Timeout for {} ms", HystrixProxy.invokeTimeout);
            return  new WechatCorporationServiceFactory(-1,"WechatCorporationService Timeout for " + HystrixProxy.invokeTimeout + " ms");
        } else {
            LOGGER.error("WechatCorporationService Fallback caused by: ",throwable);
            return  new WechatCorporationServiceFactory(-1,"WechatCorporationService Fallback caused by:" + throwable.getMessage());
        }
    }
}