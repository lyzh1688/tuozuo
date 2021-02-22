package com.tuozuo.tavern.corp.assist.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tuozuo.tavern.corp.assist.model.BusinessDict;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2021/2/20 <br>
 */
public interface BusinessDictService {

    boolean saveDict(BusinessDict businessDict);

    boolean delDict(String dictId);

    boolean modifyDict(BusinessDict businessDict);

    IPage<BusinessDict> queryDicts(int pageNo,int pageSize, String businessName);

}
