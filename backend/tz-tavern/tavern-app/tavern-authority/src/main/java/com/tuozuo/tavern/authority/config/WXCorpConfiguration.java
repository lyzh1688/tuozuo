package com.tuozuo.tavern.authority.config;

import com.tuozuo.tavern.authority.property.MiniAppConfig;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.api.impl.WxCpServiceImpl;
import me.chanjar.weixin.cp.config.impl.WxCpDefaultConfigImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WXCorpConfiguration {
    @Autowired
    MiniAppConfig miniAppConfig;

    @Bean
    public WxCpService wxCpService(){
        WxCpDefaultConfigImpl config = new WxCpDefaultConfigImpl();
        config.setCorpId(miniAppConfig.getCorpID());
        config.setCorpSecret(miniAppConfig.getCorpSecret());
        WxCpService service = new WxCpServiceImpl();
        service.setWxCpConfigStorage(config);
        return service;
    }
}
