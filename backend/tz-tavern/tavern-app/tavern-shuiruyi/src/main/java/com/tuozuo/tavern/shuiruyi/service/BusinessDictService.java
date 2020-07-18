package com.tuozuo.tavern.shuiruyi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tuozuo.tavern.shuiruyi.model.BusinessDict;

import java.util.List;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/7/18 <br>
 */
public interface BusinessDictService  extends IService<BusinessDict> {

    List<BusinessDict> queryBuinessDicts(String group);

}
