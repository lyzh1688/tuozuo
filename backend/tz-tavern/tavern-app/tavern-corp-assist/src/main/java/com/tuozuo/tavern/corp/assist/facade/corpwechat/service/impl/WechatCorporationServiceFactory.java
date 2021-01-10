package com.tuozuo.tavern.corp.assist.facade.corpwechat.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.tuozuo.tavern.corp.assist.facade.corpwechat.model.AccessToken;
import com.tuozuo.tavern.corp.assist.facade.corpwechat.model.ClientInfo;
import com.tuozuo.tavern.corp.assist.facade.corpwechat.model.GroupChat;
import com.tuozuo.tavern.corp.assist.facade.corpwechat.model.GroupChatDetail;
import com.tuozuo.tavern.corp.assist.facade.corpwechat.service.WechatCorporationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2021/1/9 <br>
 */
public class WechatCorporationServiceFactory extends BaseServiceFactory implements WechatCorporationService {
    private static final Logger LOGGER = LoggerFactory.getLogger(WechatCorporationServiceFactory.class);


    public WechatCorporationServiceFactory(int errorCode, String errorInfo) {
        super(errorCode, errorInfo);
    }

    @Override
    public AccessToken getAccessToken(String id, String secret) {
        LOGGER.error("[企业微信查询AccessToken错误] id: {},secret: {}", id, secret);
        return null;
    }

    @Override
    public GroupChatDetail getGroupChat(String accessToken, JSONObject object) {
        LOGGER.error("[企业微信查询企业群错误] accessToken: {} requestBody: {}", accessToken, object.toJSONString());
        return null;
    }

    @Override
    public ClientInfo getClientInfo(String accessToken, String externalUserId) {
        LOGGER.error("[企业微信查询客户信息错误] accessToken: {},externalUserId: {}", accessToken, externalUserId);
        return null;
    }
}
