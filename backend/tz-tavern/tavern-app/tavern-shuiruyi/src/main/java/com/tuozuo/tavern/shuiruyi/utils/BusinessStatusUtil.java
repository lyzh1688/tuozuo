package com.tuozuo.tavern.shuiruyi.utils;

import java.util.Map;

import com.google.common.collect.Maps;
import com.tuozuo.tavern.shuiruyi.dict.BusinessStatus;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/7/18 <br>
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
