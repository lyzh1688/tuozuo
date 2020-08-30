package com.tuozuo.tavern.xinruyi.dao;

import com.tuozuo.tavern.xinruyi.model.AreaInfo;
import com.tuozuo.tavern.xinruyi.model.BankInfo;

import java.util.List;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/8/29 <br>
 */
public interface BusinessSearchDao {

    List<BankInfo> selectBankInfo(String bankCode);

    List<AreaInfo> selectAreaInfo(String areaCode, String areaLevel);

}
