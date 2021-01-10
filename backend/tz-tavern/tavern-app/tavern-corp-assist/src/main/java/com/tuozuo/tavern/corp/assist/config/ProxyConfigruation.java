package com.tuozuo.tavern.corp.assist.config;

import com.tuoyou.tavern.rpc.libs.core.hystrix.HystrixProxy;
import com.tuozuo.tavern.corp.assist.facade.corpwechat.service.WechatCorporationService;
import com.tuozuo.tavern.corp.assist.facade.corpwechat.service.impl.WechatCorporationServiceFallback;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/11/23 <br>
 */
@Configuration
public class ProxyConfigruation {

    @Value("${wechat.corp.http.url:https://qyapi.weixin.qq.com}")
    private String qccUrl;

    @Bean(name = "wechatCorporationService")
    public WechatCorporationService wechatCorporationService() {
        return new HystrixProxy<WechatCorporationService>().newProxy(WechatCorporationService.class, qccUrl, new WechatCorporationServiceFallback());
    }

}
