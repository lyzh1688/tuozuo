package com.tuozuo.tavern.corp.assist.facade.corpwechat.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseServiceFactory {
    final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private int errorCode;
    private String errorInfo;


    public BaseServiceFactory(int errorCode, String errorInfo) {
        this.errorCode = errorCode;
        this.errorInfo = errorInfo;
    }

}