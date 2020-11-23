package com.tuoyou.tavern.rpc.libs.core.hystrix;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import feign.Request;
import feign.RequestTemplate;
import feign.Target;

/**
 * Created by liuyuezhi on 2018/6/21.
 */
public class DynamicTarget<T> implements Target<T> {
    final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private Class<T> type;
    private String name;
    private String url;
    private String group;

    public DynamicTarget(Class<T> type, String url) {
        this.type = type;
        this.name = url;
        this.url = url;
    }

    public void reset(String url) {
        this.name = url;
        this.url = url;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    @Override
    public Class<T> type() {
        return this.type;
    }

    @Override
    public String name() {
        return this.name;
    }

    @Override
    public String url() {
        return this.url;
    }

    @Override
    public Request apply(RequestTemplate input) {
        String newUrl = TargetSelection.instance.chooseTarget(this.group);
        this.url = newUrl;
//        LOGGER.info("choose url : {}" ,this.url);
        if(input.url().indexOf("com/orientsec/obgear/quote/etl/facility/http") != 0) {
            input.insert(0, this.url());
        }

        return input.request();
    }
}
