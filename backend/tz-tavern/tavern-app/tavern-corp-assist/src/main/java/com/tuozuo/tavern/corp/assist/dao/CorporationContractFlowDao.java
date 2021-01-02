package com.tuozuo.tavern.corp.assist.dao;

import com.tuozuo.tavern.corp.assist.model.CorporationContractFlow;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/12/24 <br>
 */
public interface CorporationContractFlowDao {

    CorporationContractFlow selectById(String flowId);

}
