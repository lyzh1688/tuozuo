package com.tuozuo.tavern.xinruyi.service;


import com.tuozuo.tavern.xinruyi.model.BusinessDict;

import java.util.List;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/7/18 <br>
 */
public interface BusinessDictService {

    List<BusinessDict> queryBuinessDicts(String group);

}
