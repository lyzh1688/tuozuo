package com.tuozuo.tavern.shuiruyi.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tuozuo.tavern.shuiruyi.model.ContractInfo;
import com.tuozuo.tavern.shuiruyi.model.ContractTemplate;
import com.tuozuo.tavern.shuiruyi.service.ContractInfoService;
import com.tuozuo.tavern.shuiruyi.vo.ContractInfoVO;
import com.tuozuo.tavern.shuiruyi.vo.ContractModifyVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/7/25 <br>
 */
@Service
public class ContractInfoServiceImpl implements ContractInfoService {
    @Override
    public IPage<ContractInfo> queryContractList(String companyId, int pageNo, int pageSize) {
        return null;
    }

    @Override
    public void addContractInfo(ContractInfoVO vo) {

    }

    @Override
    public List<ContractTemplate> queryContractTemplateList() {
        return null;
    }

    @Override
    public List<ContractInfo> fuzzyQueryContractInfo(String contractStatus, String contractName, int queryCnt, String roleGroup, String customId) {
        return null;
    }

    @Override
    public void modifyContractInfo(ContractModifyVO contractModifyVO) {
        
    }
}
