package com.tuozuo.tavern.shuiruyi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tuozuo.tavern.shuiruyi.model.ContractDetailInfo;
import com.tuozuo.tavern.shuiruyi.model.ContractInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ContractInfoMapper extends BaseMapper<ContractInfo> {
    IPage<ContractDetailInfo> selectList(Page page,
                                         @Param("companyId") String companyId,
                                         @Param("customId") String customId);

    List<ContractInfo> fuzzyQuery(@Param("contractStatus") String contractStatus,
                                  @Param("contractName") String contractName,
                                  @Param("queryCnt") int queryCnt,
                                  @Param("customId") String customId);

    ContractDetailInfo select(@Param("contractId") String contractId);

    ContractDetailInfo selectInvoiced(@Param("contractId") String contractId);
}