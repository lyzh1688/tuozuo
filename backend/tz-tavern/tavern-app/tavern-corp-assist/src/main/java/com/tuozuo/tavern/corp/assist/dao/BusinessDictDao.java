package com.tuozuo.tavern.corp.assist.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tuozuo.tavern.corp.assist.model.BusinessDict;

import java.util.List;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2021/2/20 <br>
 */
public interface BusinessDictDao {

    boolean insertDict(BusinessDict businessDict);

    boolean delDict(String dictId);

    boolean updateDict(BusinessDict businessDict);

    List<BusinessDict> selectDicts(String businessName, String businessGroup);

}
