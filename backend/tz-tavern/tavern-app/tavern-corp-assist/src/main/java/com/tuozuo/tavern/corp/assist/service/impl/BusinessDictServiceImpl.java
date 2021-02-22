package com.tuozuo.tavern.corp.assist.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tuozuo.tavern.corp.assist.dao.BusinessDictDao;
import com.tuozuo.tavern.corp.assist.model.BusinessDict;
import com.tuozuo.tavern.corp.assist.service.BusinessDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2021/2/22 <br>
 */
@Service
public class BusinessDictServiceImpl implements BusinessDictService {
    @Autowired
    private BusinessDictDao businessDictDao;

    @Override
    public boolean saveDict(BusinessDict businessDict) {
        return this.businessDictDao.insertDict(businessDict);
    }

    @Override
    public boolean delDict(String groupId) {
        return this.businessDictDao.delDict(dictId);
    }

    @Override
    public boolean modifyDict(BusinessDict businessDict) {
        return false;
    }

    @Override
    public IPage<BusinessDict> queryDicts(int pageNo, int pageSize, String businessName) {
        return null;
    }
}
