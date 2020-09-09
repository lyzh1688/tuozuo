package com.tuozuo.tavern.xinruyi.service;

import com.tuozuo.tavern.xinruyi.model.AreaInfo;
import com.tuozuo.tavern.xinruyi.model.BankInfo;

import java.util.List;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/8/29 <br>
 */
public interface BusinessSearchService {

    List<BankInfo> queryBankInfo(String bankCode,String bankName,int queryCnt);

    List<AreaInfo> queryAreaInfo(String areaCode, String areaLevel);
}
