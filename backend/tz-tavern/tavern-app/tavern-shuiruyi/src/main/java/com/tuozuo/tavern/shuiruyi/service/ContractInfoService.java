package com.tuozuo.tavern.shuiruyi.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tuozuo.tavern.shuiruyi.model.ContractInfo;
import com.tuozuo.tavern.shuiruyi.model.ContractTemplate;
import com.tuozuo.tavern.shuiruyi.vo.ContractInfoVO;

import java.util.List;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/7/25 <br>
 */
public interface ContractInfoService {

    IPage<ContractInfo> queryContractList(String companyId, int pageNo, int pageSize);

    void addContractInfo(ContractInfoVO vo);

    List<ContractTemplate> queryContractTemplateList();

}
