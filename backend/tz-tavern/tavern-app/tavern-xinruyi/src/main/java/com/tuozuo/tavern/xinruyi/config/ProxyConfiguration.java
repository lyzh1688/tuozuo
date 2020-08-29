package com.tuozuo.tavern.xinruyi.config;

import com.tuoyou.tavern.rpc.libs.core.HttpProxy;
import com.tuuozuo.tavern.authority.spi.AuthorityService;
import com.tuuozuo.tavern.authority.spi.AuthorityServiceProviderDesc;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2019/07/02 <br>
 */
@Configuration
public class ProxyConfiguration {

    @Bean
    public AuthorityService authorityService(){
        return new HttpProxy<AuthorityService>("http://127.0.0.1:8081",AuthorityService.class, AuthorityServiceProviderDesc.serviceProviderDesc.get(AuthorityService.class)).getProxy();
    }
}
