package com.tuozuo.tavern.corp.assist.facade.corpwechat.service;

import com.tuozuo.tavern.corp.assist.facade.corpwechat.model.AccessToken;
import com.tuozuo.tavern.corp.assist.facade.corpwechat.model.ClientInfo;
import com.tuozuo.tavern.corp.assist.facade.corpwechat.model.GroupChatDetail;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2021/1/9 <br>
 */
public interface WechatGroupChatService {

    AccessToken getAccessToken();

    GroupChatDetail getGroupChat(String accessToken,String chatId);


    ClientInfo getClientInfo(String accessToken, String externalUserId);

}
