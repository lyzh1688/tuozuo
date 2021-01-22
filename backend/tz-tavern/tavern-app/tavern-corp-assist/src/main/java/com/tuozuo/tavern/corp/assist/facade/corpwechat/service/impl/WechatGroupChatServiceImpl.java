package com.tuozuo.tavern.corp.assist.facade.corpwechat.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.tuozuo.tavern.corp.assist.facade.corpwechat.dict.MemberType;
import com.tuozuo.tavern.corp.assist.facade.corpwechat.model.*;
import com.tuozuo.tavern.corp.assist.facade.corpwechat.service.WechatCorporationService;
import com.tuozuo.tavern.corp.assist.facade.corpwechat.service.WechatGroupChatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2021/1/9 <br>
 */
@Service
public class WechatGroupChatServiceImpl implements WechatGroupChatService {

    private static final Logger LOGGER = LoggerFactory.getLogger(WechatGroupChatServiceImpl.class);

    @Value("${wechat.corp.id:ww44d16a013b30d82c}")
    private String id;
    @Value("${wechat.corp.secret:7eN2czjpUPaea2yvrMYhGvwbdwwuZzLd0WnlxD7DFTY}")
    private String secret;

    @Autowired
    private WechatCorporationService wechatCorporationService;

    @Override
    public AccessToken getAccessToken() {
        AccessToken accessToken = this.wechatCorporationService.getAccessToken(id, secret);
        if (accessToken == null) {
            return null;
        }
        if (isAbnormalResponse(accessToken.getErrCode())) {
            LOGGER.error("[获取accessToken出错]  errorcode: {} errormsg: {}", accessToken.getErrCode(), accessToken.getErrMsg());
            return null;
        }
        return accessToken;
    }

    @Override
    public GroupChatDetail getGroupChat(String accessToken, String chatId) {
        JSONObject requestBody = new JSONObject();
        requestBody.put("chat_id", chatId);
        GroupChatDetail groupChatDetail = this.wechatCorporationService.getGroupChat(accessToken, requestBody);
        if (groupChatDetail == null) {
            return null;
        }
        if (isAbnormalResponse(groupChatDetail.getErrCode())) {
            LOGGER.error("[获取企业微信群信息出错]  errorcode: {} errormsg: {}", groupChatDetail.getErrCode(), groupChatDetail.getErrMsg());
            return null;
        }
        if (groupChatDetail.getGroupChat() == null || groupChatDetail.getGroupChat().getMemberList().size() == 0) {
            return null;
        }
        Map<String, List<MemberList>> memberListMap = groupChatDetail.getGroupChat().getMemberList().stream()
                .collect(Collectors.groupingBy(MemberList::getType));
        if (memberListMap.containsKey(MemberType.EXTERNAL_USER.getType())) {
            List<MemberList> memberLists = memberListMap.get(MemberType.EXTERNAL_USER.getType());
            GroupChat groupChat = groupChatDetail.getGroupChat();
            groupChat.setMemberList(memberLists);
            groupChatDetail.setGroupChat(groupChat);
            return groupChatDetail;
        } else {
            return null;
        }
    }

    @Override
    public ClientInfo getClientInfo(String accessToken, String externalUserId) {
        ClientInfo clientInfo = this.wechatCorporationService.getClientInfo(accessToken, externalUserId);
        if (clientInfo == null) {
            return null;
        }
        if (isAbnormalResponse(clientInfo.getErrCode())) {
            LOGGER.error("[获取客户信息出错] externalUserId: {} errorcode: {} errormsg: {}", externalUserId, clientInfo.getErrCode(), clientInfo.getErrMsg());
            return clientInfo;
        } else {
            return clientInfo;
        }
    }

    private boolean isAbnormalResponse(final int status) {
        return status != 0;
    }
}
