package com.tuozuo.tavern.corp.assist.dao.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tuozuo.tavern.corp.assist.dao.CompanyContractTemplateDao;
import com.tuozuo.tavern.corp.assist.mapper.CompanyContractTemplateMapper;
import com.tuozuo.tavern.corp.assist.model.CompanyContractTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/12/24 <br>
 */
@Repository
public class CompanyContractTemplateDaoImpl extends ServiceImpl<CompanyContractTemplateMapper, CompanyContractTemplate> implements CompanyContractTemplateDao {

    @Override
    public List<CompanyContractTemplate> selectAll() {
        return this.list();
    }

    @Override
    public CompanyContractTemplate selectById(String templateId) {
        return this.getById(templateId);
    }
}
