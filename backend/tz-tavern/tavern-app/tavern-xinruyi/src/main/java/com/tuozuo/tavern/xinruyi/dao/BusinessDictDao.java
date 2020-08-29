package com.tuozuo.tavern.xinruyi.dao;


import com.tuozuo.tavern.xinruyi.model.BusinessDict;

import java.util.List;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/7/18 <br>
 */
public interface BusinessDictDao {
    List<BusinessDict> selectBuinessDicts(String group);
}
