package com.tuozuo.tavern.corp.assist.facade.corpwechat.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2021/1/9 <br>
 */
public class AccessToken {

    @JsonProperty("errcode")
    private int errCode;
    @JsonProperty("errmsg")
    private String errMsg;
    @JsonProperty("access_token")
    private String accessToken;

    public int getErrCode() {
        return errCode;
    }

    public void setErrCode(int errCode) {
        this.errCode = errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
