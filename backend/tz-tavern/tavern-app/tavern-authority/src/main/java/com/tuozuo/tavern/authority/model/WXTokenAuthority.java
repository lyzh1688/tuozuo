package com.tuozuo.tavern.authority.model;

/**
 * Created by 刘悦之 on 2020/10/10.
 */
public class WXTokenAuthority extends TokenAuthority{
    String openID;

    public String getOpenID() {
        return openID;
    }

    public void setOpenID(String openID) {
        this.openID = openID;
    }
}
