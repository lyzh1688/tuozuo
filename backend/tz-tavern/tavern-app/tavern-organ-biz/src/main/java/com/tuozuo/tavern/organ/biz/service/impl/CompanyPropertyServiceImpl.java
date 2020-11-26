package com.tuozuo.tavern.organ.biz.service.impl;

import com.tuozuo.tavern.organ.biz.dao.CompanyPropertyDao;
import com.tuozuo.tavern.organ.biz.dict.CompanyPropertyType;
import com.tuozuo.tavern.organ.biz.model.CompanyNameProperty;
import com.tuozuo.tavern.organ.biz.service.CompanyPropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/11/26 <br>
 */
@Service
public class CompanyPropertyServiceImpl implements CompanyPropertyService {

    @Autowired
    private CompanyPropertyDao companyPropertyDao;

    @Override
    public List<CompanyNameProperty> queryCompanyProperty(CompanyPropertyType type) {
        return this.companyPropertyDao.selectByProperty(type.name());
    }
}
