package com.tuozuo.tavern.shuiruyi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tuozuo.tavern.shuiruyi.model.InvoiceDetailInfo;
import com.tuozuo.tavern.shuiruyi.model.InvoiceInfo;
import com.tuozuo.tavern.shuiruyi.model.InvoiceStatistic;
import com.tuozuo.tavern.shuiruyi.model.TaxStatistic;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface InvoiceInfoMapper extends BaseMapper<InvoiceInfo> {

    IPage<InvoiceStatistic> selectStatistic(Page page,
                                            @Param("beginMonth") String beginMonth,
                                            @Param("endMonth") String endMonth,
                                            @Param("companyId") String companyId,
                                            @Param("customId") String customId);

    IPage<InvoiceDetailInfo> selectList(Page page,
                                        @Param("companyId") String companyId,
                                        @Param("contractId") String contractId,
                                        @Param("invoiceStatus") String invoiceStatus,
                                        @Param("customId") String customId);

    InvoiceDetailInfo select(@Param("invoiceId") String invoiceId);


    IPage<TaxStatistic> selectTaxStatistic(Page page,
                                    @Param("registerArea") String registerArea,
                                    @Param("customId") String customId,
                                    @Param("areaLevel") String areaLevel,
                                    @Param("areaCode") String areaCode,
                                    @Param("invoiceType") String invoiceType,
                                    @Param("beginDate") String beginDate,
                                    @Param("endDate") String endDate);

    TaxStatistic selectTotalTaxStatistic(@Param("registerArea") String registerArea,
                                    @Param("customId") String customId,
                                    @Param("areaLevel") String areaLevel,
                                    @Param("areaCode") String areaCode,
                                    @Param("invoiceType") String invoiceType,
                                    @Param("beginDate") String beginDate,
                                    @Param("endDate") String endDate);

}