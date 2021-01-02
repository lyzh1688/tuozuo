package com.tuozuo.tavern.corp.assist.dao.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tuozuo.tavern.corp.assist.dao.CorporationContractFlowDao;
import com.tuozuo.tavern.corp.assist.mapper.CorporationContractFlowMapper;
import com.tuozuo.tavern.corp.assist.model.CorporationContractFlow;
import org.springframework.stereotype.Repository;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/12/24 <br>
 */
@Repository
public class CorporationContractFlowDaoImpl extends ServiceImpl<CorporationContractFlowMapper, CorporationContractFlow> implements CorporationContractFlowDao {
    @Override
    public CorporationContractFlow selectById(String flowId) {
        return this.getById(flowId);
    }
}
