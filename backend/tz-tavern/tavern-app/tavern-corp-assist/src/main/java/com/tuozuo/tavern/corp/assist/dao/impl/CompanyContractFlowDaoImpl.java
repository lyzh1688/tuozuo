package com.tuozuo.tavern.corp.assist.dao.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tuozuo.tavern.corp.assist.dao.CompanyContractFlowDao;
import com.tuozuo.tavern.corp.assist.mapper.CompanyContractFlowMapper;
import com.tuozuo.tavern.corp.assist.model.CompanyContractFlow;
import org.springframework.stereotype.Repository;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/12/24 <br>
 */
@Repository
public class CompanyContractFlowDaoImpl extends ServiceImpl<CompanyContractFlowMapper, CompanyContractFlow> implements CompanyContractFlowDao {
    @Override
    public CompanyContractFlow selectById(String flowId) {
        return this.getById(flowId);
    }
}
