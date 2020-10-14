package com.tuozuo.tavern.authority.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by 刘悦之 on 2020/10/13.
 */
@Component
@ConfigurationProperties(prefix = "wx.miniapp.configs")
public class MiniAppConfig {
    String appID;
    String secret;

    public String getAppID() {
        return appID;
    }

    public void setAppID(String appID) {
        this.appID = appID;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }
}
