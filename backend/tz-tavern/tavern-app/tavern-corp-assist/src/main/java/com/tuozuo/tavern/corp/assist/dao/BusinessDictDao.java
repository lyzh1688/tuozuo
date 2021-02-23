package com.tuozuo.tavern.corp.assist.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tuozuo.tavern.corp.assist.model.BusinessDict;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2021/2/20 <br>
 */
public interface BusinessDictDao {

    boolean insertDict(BusinessDict businessDict);

    boolean delDict(String dictId);

    boolean updateDict(BusinessDict businessDict);

    IPage<BusinessDict> selectDicts(Page<BusinessDict> page, String businessName,String businessGroup);

}
