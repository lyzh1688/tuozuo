package com.tuozuo.tavern.corp.assist.facade.corpwechat.service;

import com.alibaba.fastjson.JSONObject;
import com.tuozuo.tavern.corp.assist.facade.corpwechat.model.AccessToken;
import com.tuozuo.tavern.corp.assist.facade.corpwechat.model.ClientInfo;
import com.tuozuo.tavern.corp.assist.facade.corpwechat.model.GroupChatDetail;
import feign.Headers;
import feign.Param;
import feign.RequestLine;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/11/23 <br>
 */
@Headers({"Content-Type: application/json,text/plain,text/html", "Accept: application/json,text/plain,text/html"})
public interface WechatCorporationService {

    @RequestLine("GET /cgi-bin/gettoken?corpid={id}&corpsecret={secret}")
    AccessToken getAccessToken(@Param("id") String id,
                               @Param("secret") String secret);

    @RequestLine("POST /cgi-bin/externalcontact/groupchat/get?access_token={accessToken}")
    GroupChatDetail getGroupChat(@Param("accessToken") String accessToken,
                                 @RequestBody JSONObject object);


    @RequestLine("GET /cgi-bin/externalcontact/get?access_token={accessToken}&external_userid={externalUserId}")
    ClientInfo getClientInfo(@Param("accessToken") String accessToken,
                             @Param("externalUserId") String externalUserId);


}
