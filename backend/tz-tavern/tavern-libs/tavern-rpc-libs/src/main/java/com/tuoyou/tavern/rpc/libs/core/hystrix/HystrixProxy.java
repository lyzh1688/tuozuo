package com.tuoyou.tavern.rpc.libs.core.hystrix;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.netflix.hystrix.HystrixCommandProperties;

import feign.Retryer;
import feign.hystrix.FallbackFactory;
import feign.hystrix.HystrixFeign;
import feign.hystrix.SetterFactory;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;

/**
 * Created by liuyuezhi on 2017/11/6.
 */
public class HystrixProxy<T> {

    public static final int invokeTimeout = 20000;

    final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    public T newProxy(Class<T> clazz, String url, FallbackFactory<T> fallbackFactory) {

        T t = null;
        try {
            LOGGER.info(url);
            HystrixFeign.Builder builder = HystrixFeign.builder();
            t = builder
                    .decoder(new JacksonDecoder())
                    .encoder(new JacksonEncoder())
                    .retryer(new Retryer.Default(1000L, 1000L, 5))
                    .setterFactory((target, m) -> new SetterFactory.Default().create(target, m).andCommandPropertiesDefaults(HystrixCommandProperties.defaultSetter().withExecutionTimeoutInMilliseconds(invokeTimeout)))
                    .target(clazz, url, fallbackFactory);

        } catch (Exception e) {
            LOGGER.error("HystrixProxy newProxy error:{}", e.getMessage());
        }
        return t;
    }

    public T newProxy(String name, Class<T> clazz, FallbackFactory<T> fallbackFactory) {
//        TargetSelection.instance.chooseTarget(name,clazz);
        T t = null;
        try {
            HystrixFeign.Builder builder = HystrixFeign.builder();
            t = builder
                    .decoder(new JacksonDecoder())
                    .encoder(new JacksonEncoder())
                    .setterFactory((target, m) -> new SetterFactory.Default().create(target, m).andCommandPropertiesDefaults(HystrixCommandProperties.defaultSetter().withExecutionTimeoutInMilliseconds(invokeTimeout)))
                    .retryer(new Retryer.Default(1000L, 1000L, 5))
                    .target(TargetSelection.instance.createTarget(name, clazz), fallbackFactory);
//                    .target(TargetSelection.instance.getTarget(name,clazz),fallbackFactory);
        } catch (Exception e) {
            LOGGER.error("HystrixProxy newProxy error:{}", e.getMessage());
        }
        return t;
    }

    public T newProxy(String name ,Class<T> clazz, FallbackFactory<T> fallbackFactory,HystrixParam hystrixParam){
        T t = null;
        try{
            HystrixFeign.Builder builder = HystrixFeign.builder();
            t = builder
                    .decoder(new JacksonDecoder())
                    .encoder(new JacksonEncoder())
                    .setterFactory((target,m) -> {
                        return new SetterFactory.Default().create(target,m)
                                .andCommandPropertiesDefaults(HystrixCommandProperties.defaultSetter().withExecutionTimeoutInMilliseconds(invokeTimeout))
                                .andThreadPoolPropertiesDefaults(com.netflix.hystrix.HystrixThreadPoolProperties.Setter().withCoreSize(hystrixParam.getThreadPoolCoreSize())
                                        .withMaxQueueSize(hystrixParam.getMaxQueueSizePerThread())
                                        .withQueueSizeRejectionThreshold(hystrixParam.getQueueSizeRejectionThresholdPerThread()));
                    })
                    .retryer(new Retryer.Default(hystrixParam.getRetryPeriod(), hystrixParam.getRetryMaxPeriod(), hystrixParam.getRetryMaxAttempt()))
                    .target(TargetSelection.instance.createTarget(name,clazz),fallbackFactory);
        }
        catch (Exception e){
            LOGGER.error("HystrixProxy newProxy error:{}",e.getMessage());
        }
        return t;
    }
}
