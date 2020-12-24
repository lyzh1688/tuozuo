package com.tuozuo.tavern.corp.assist.service.impl;

import com.tuozuo.tavern.corp.assist.dao.CompanyContractTemplateDao;
import com.tuozuo.tavern.corp.assist.model.CompanyContractTemplate;
import com.tuozuo.tavern.corp.assist.service.CompanyContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/12/24 <br>
 */
@Service
public class CompanyContractServiceImpl implements CompanyContractService {

    @Autowired
    private CompanyContractTemplateDao companyContractTemplateDao;


    @Override
    public List<CompanyContractTemplate> queryAllCompanyContractTemplate() {
        return this.companyContractTemplateDao.selectAll();
    }
}
