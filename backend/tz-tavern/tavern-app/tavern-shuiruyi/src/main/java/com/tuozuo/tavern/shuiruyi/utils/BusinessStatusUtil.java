package com.tuozuo.tavern.shuiruyi.utils;

import java.util.Map;

import com.google.common.collect.Maps;
import com.tuozuo.tavern.shuiruyi.dict.BusinessStatus;

/**
 * 功能说明: <br>
 * 系统说明: <br>
 * 模块说明: <br>
 * 功能描述: <br>
 * <br>
 *
 * @author hebiao@orientsec.com.cn
 * <br>
 * 软件著作: 东方证券 版权所有
 * @since 1.0
 */
public class BusinessStatusUtil {
    public static Map<String, String> registeredMap = Maps.newConcurrentMap();
    public  static Map<String, String> registeringMap = Maps.newConcurrentMap();

    static {
        registeringMap.put(BusinessStatus.REG_AUDIT_NAME.name(),BusinessStatus.REG_AUDIT_NAME.name());
        registeringMap.put(BusinessStatus.REG_AUDIT_TAX.name(),BusinessStatus.REG_AUDIT_TAX.name());

        registeredMap.put(BusinessStatus.REG_DONE.name(),BusinessStatus.REG_DONE.name());
        registeredMap.put(BusinessStatus.SERVICE.name(),BusinessStatus.SERVICE.name());
        registeredMap.put(BusinessStatus.ARREARAGE.name(),BusinessStatus.ARREARAGE.name());
        registeredMap.put(BusinessStatus.TAX_CANCEL.name(),BusinessStatus.TAX_CANCEL.name());
        registeredMap.put(BusinessStatus.BIZ_CANCEL.name(),BusinessStatus.BIZ_CANCEL.name());
        registeredMap.put(BusinessStatus.FINISH.name(),BusinessStatus.FINISH.name());
    }



}
