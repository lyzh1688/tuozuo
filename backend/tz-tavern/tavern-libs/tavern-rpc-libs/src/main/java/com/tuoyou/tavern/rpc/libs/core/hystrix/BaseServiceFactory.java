package com.tuoyou.tavern.rpc.libs.core.hystrix;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by liuyuezhi on 2018/1/26.
 */
public class BaseServiceFactory {
    final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private int errorCode;
    private String errorInfo;


    public BaseServiceFactory(int errorCode, String errorInfo) {
        this.errorCode = errorCode;
        this.errorInfo = errorInfo;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorInfo() {
        return errorInfo;
    }

    public void setErrorInfo(String errorInfo) {
        this.errorInfo = errorInfo;
    }
}
