package com.tuozuo.tavern.shuiruyi.dao.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tuozuo.tavern.shuiruyi.dao.ContractInfoDao;
import com.tuozuo.tavern.shuiruyi.mapper.ContractInfoMapper;
import com.tuozuo.tavern.shuiruyi.mapper.ContractTemplateMapper;
import com.tuozuo.tavern.shuiruyi.model.ContractDetailInfo;
import com.tuozuo.tavern.shuiruyi.model.ContractInfo;
import com.tuozuo.tavern.shuiruyi.model.ContractTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/7/28 <br>
 */
@Repository
public class ContractInfoDaoImpl implements ContractInfoDao {

    @Autowired
    private ContractInfoMapper contractInfoMapper;
    @Autowired
    private ContractTemplateMapper contractTemplateMapper;

    @Override
    public IPage<ContractDetailInfo> queryContractList(String companyId, int pageNo, int pageSize, String customId) {
        Page page = new Page(pageNo, pageSize);
        return this.contractInfoMapper.selectList(page, companyId, customId);
    }

    @Override
    public void insert(ContractInfo contractInfo) {
        this.contractInfoMapper.insert(contractInfo);
    }

    @Override
    public void update(ContractInfo contractInfo) {
        this.contractInfoMapper.updateById(contractInfo);
    }

    @Override
    public List<ContractTemplate> selectTemplateList() {
        return this.contractTemplateMapper.selectAll();
    }

    @Override
    public List<ContractInfo> fuzzyQueryContractInfo(String contractStatus, String contractName, int queryCnt, String customId) {
        return this.contractInfoMapper.fuzzyQuery(contractStatus, contractName, queryCnt, customId);
    }

    @Override
    public ContractDetailInfo queryContractDetail(String contractId) {
        return this.contractInfoMapper.select(contractId);
    }
}
