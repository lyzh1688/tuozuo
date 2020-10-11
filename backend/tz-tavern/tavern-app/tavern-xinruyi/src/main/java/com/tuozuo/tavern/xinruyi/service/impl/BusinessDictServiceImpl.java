package com.tuozuo.tavern.xinruyi.service.impl;

import com.tuozuo.tavern.xinruyi.dao.BusinessDictDao;
import com.tuozuo.tavern.xinruyi.model.BusinessDict;
import com.tuozuo.tavern.xinruyi.service.BusinessDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/7/18 <br>
 */
@Service
public class BusinessDictServiceImpl implements BusinessDictService {

    @Autowired
    private BusinessDictDao businessDictDao;

    @Override
    public List<BusinessDict> queryBusinessDicts(String group) {
        return this.businessDictDao.selectBuinessDicts(group);
    }

    @Override
    public List<BusinessDict> queryIndustryType() {
        return this.businessDictDao.selectIndustryType();
    }
}
