package com.tuozuo.tavern.authority.config;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.config.impl.WxMaDefaultConfigImpl;
import com.tuozuo.tavern.authority.property.MiniAppConfig;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by 刘悦之 on 2020/10/13.
 */
public class WXConfigration {

    @Autowired
    MiniAppConfig miniAppConfig;

    public WxMaService WXMaService() {
        WxMaDefaultConfigImpl config = new WxMaDefaultConfigImpl();
        config.setAppid(miniAppConfig.getAppID());
        config.setSecret(miniAppConfig.getSecret());
        WxMaService service = new WxMaServiceImpl();
        service.setWxMaConfig(config);
        return service;
    }
}
