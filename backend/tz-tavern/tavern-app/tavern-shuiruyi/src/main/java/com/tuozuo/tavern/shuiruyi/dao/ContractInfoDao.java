package com.tuozuo.tavern.shuiruyi.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tuozuo.tavern.shuiruyi.model.ContractDetailInfo;
import com.tuozuo.tavern.shuiruyi.model.ContractInfo;
import com.tuozuo.tavern.shuiruyi.model.ContractTemplate;

import java.util.List;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/7/28 <br>
 */
public interface ContractInfoDao {

    IPage<ContractDetailInfo> queryContractList(String companyId, int pageNo, int pageSize, String customId);

    void insert(ContractInfo contractInfo);

    void update(ContractInfo contractInfo);

    List<ContractTemplate> selectTemplateList();

    List<ContractInfo> fuzzyQueryContractInfo(String contractStatus, String contractName, int queryCnt, String customId);

    ContractDetailInfo selectContractDetail(String contractId);

    ContractDetailInfo selectInvoicedContract(String contractId);


}
