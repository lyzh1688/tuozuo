package com.tuozuo.tavern.shuiruyi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tuozuo.tavern.shuiruyi.model.InvoiceInfo;
import com.tuozuo.tavern.shuiruyi.model.InvoiceStatistic;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface InvoiceInfoMapper extends BaseMapper<InvoiceInfo> {

    IPage<InvoiceStatistic> selectStatistic(Page page,
                                            @Param("beginMonth") String beginMonth,
                                            @Param("endMonth") String endMonth,
                                            @Param("companyId") String companyId,
                                            @Param("customId") String customId);


}