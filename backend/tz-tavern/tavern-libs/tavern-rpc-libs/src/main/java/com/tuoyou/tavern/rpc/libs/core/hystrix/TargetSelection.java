package com.tuoyou.tavern.rpc.libs.core.hystrix;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.Server;

import feign.Target;

/**
 * Created by liuyuezhi on 2018/6/21.
 */
public class TargetSelection {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    public static final TargetSelection instance = new TargetSelection();

    private Map<String,IRule> ruleMap = Maps.newHashMap();
    private Map<String,Target> targetMap = Maps.newHashMap();
    private TargetSelection(){}

    public void addRule(String name,IRule rule){
        this.ruleMap.put(name,rule);
    }

//    public <T> Target<T> chooseTarget(String name ,Class<T> clazz){
//        Server server = this.ruleMap.get(name).choose(null);
//        if(targetMap.get(name) != null){
//            ((DynamicTarget)targetMap.get(name)).reset(server.getId());
//        }
//
//        LOGGER.info("chooseTarget : {}" ,server.getId());
//        Target target = new DynamicTarget<T>(clazz,server.getId());
//        this.targetMap.put(name,target);
//        return target;
//    }


    public <T> Target<T> getTarget(String name,Class<T> clazz){
        return this.targetMap.get(name);
    }

    public <T> Target<T> createTarget(String name,Class<T> clazz){
        Server server = this.ruleMap.get(name).choose(null);
        DynamicTarget target = new DynamicTarget<T>(clazz,server.getId());
        target.setGroup(name);
        return target;
    }

    public String chooseTarget(String name){
        String target = this.ruleMap.get(name).choose(null).getId();
        LOGGER.info("chooseTarget:{}",target);
        return target;
    }
}
