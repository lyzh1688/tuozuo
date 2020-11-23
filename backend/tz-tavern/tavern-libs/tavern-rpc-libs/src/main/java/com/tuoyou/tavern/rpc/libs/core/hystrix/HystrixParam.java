package com.tuoyou.tavern.rpc.libs.core.hystrix;

import java.util.concurrent.TimeUnit;

/**
 * Created by liuyuezhi on 2019/1/19.
 */
public class HystrixParam {

    int invokeTimeout;
    int threadPoolCoreSize = 10;// 默认10
    int maxQueueSizePerThread = -1;// 默认-1
    int queueSizeRejectionThresholdPerThread = 5;//默认5，maxQueueSize=-1 时不起作用
    long retryPeriod = 100;// 默认100
    long retryMaxPeriod = TimeUnit.SECONDS.toMillis(1L);// 默认java.util.concurrent.TimeUnit.SECONDS.toMillis(1L)
    int retryMaxAttempt = 5;// 默认5

    public int getInvokeTimeout() {
        return invokeTimeout;
    }

    public void setInvokeTimeout(int invokeTimeout) {
        this.invokeTimeout = invokeTimeout;
    }

    public int getThreadPoolCoreSize() {
        return threadPoolCoreSize;
    }

    public void setThreadPoolCoreSize(int threadPoolCoreSize) {
        this.threadPoolCoreSize = threadPoolCoreSize;
    }

    public int getMaxQueueSizePerThread() {
        return maxQueueSizePerThread;
    }

    public void setMaxQueueSizePerThread(int maxQueueSizePerThread) {
        this.maxQueueSizePerThread = maxQueueSizePerThread;
    }

    public int getQueueSizeRejectionThresholdPerThread() {
        return queueSizeRejectionThresholdPerThread;
    }

    public void setQueueSizeRejectionThresholdPerThread(int queueSizeRejectionThresholdPerThread) {
        this.queueSizeRejectionThresholdPerThread = queueSizeRejectionThresholdPerThread;
    }

    public long getRetryPeriod() {
        return retryPeriod;
    }

    public void setRetryPeriod(long retryPeriod) {
        this.retryPeriod = retryPeriod;
    }

    public long getRetryMaxPeriod() {
        return retryMaxPeriod;
    }

    public void setRetryMaxPeriod(long retryMaxPeriod) {
        this.retryMaxPeriod = retryMaxPeriod;
    }

    public int getRetryMaxAttempt() {
        return retryMaxAttempt;
    }

    public void setRetryMaxAttempt(int retryMaxAttempt) {
        this.retryMaxAttempt = retryMaxAttempt;
    }
}
