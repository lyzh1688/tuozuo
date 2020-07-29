package com.tuozuo.tavern.shuiruyi.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tuozuo.tavern.shuiruyi.model.ContractDetailInfo;
import com.tuozuo.tavern.shuiruyi.model.ContractInfo;
import com.tuozuo.tavern.shuiruyi.model.ContractTemplate;
import com.tuozuo.tavern.shuiruyi.vo.ContractInfoVO;
import com.tuozuo.tavern.shuiruyi.vo.ContractModifyVO;

import java.util.List;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/7/25 <br>
 */
public interface ContractInfoService {

    IPage<ContractDetailInfo> queryContractList(String companyId, int pageNo, int pageSize, String customId, String customGroup);

    void addContractInfo(ContractInfoVO vo) throws Exception;

    List<ContractTemplate> queryContractTemplateList();

    List<ContractInfo> fuzzyQueryContractInfo(String contractStatus, String contractName, int queryCnt, String roleGroup, String customId);

    void modifyContractInfo(ContractModifyVO contractModifyVO) throws Exception;

    void auditContractInfo(String contractId,String contractStatus,String remark);

    ContractDetailInfo queryContractDetail(String contractId);
}
