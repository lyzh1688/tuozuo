package com.tuozuo.tavern.shuiruyi.dao.impl;

import com.tuozuo.tavern.shuiruyi.dao.ContractAuditFlowDao;
import com.tuozuo.tavern.shuiruyi.mapper.ContractAuditFlowMapper;
import com.tuozuo.tavern.shuiruyi.model.ContractAuditFlow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/8/1 <br>
 */
@Repository
public class ContractAuditFlowDaoImpl implements ContractAuditFlowDao {

    @Autowired
    private ContractAuditFlowMapper contractAuditFlowMapper;

    @Override
    public void insert(ContractAuditFlow contractAuditFlow) {
        this.contractAuditFlowMapper.insert(contractAuditFlow);
    }
}
