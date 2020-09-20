package com.tuozuo.tavern.xinruyi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tuozuo.tavern.xinruyi.model.ProjectPaymentDetail;
import com.tuozuo.tavern.xinruyi.model.StaffSalaryInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProjectPaymentDetailMapper extends BaseMapper<ProjectPaymentDetail> {

    int insertInitial(@Param("companyId") String companyId,
                      @Param("month") String month,
                      @Param("payDate") String payDate,
                      @Param("paymentId") String paymentId,
                      @Param("projectId") String projectId);

    IPage<StaffSalaryInfo> selectStaffDetail(Page page,
                                             @Param("companyId") String companyId,
                                             @Param("paymentId") String paymentId,
                                             @Param("projectId") String projectId,
                                             @Param("startDate") String startDate,
                                             @Param("endDate") String endDate,
                                             @Param("staffId") String staffId);

    IPage<StaffSalaryInfo> selectStaffInfo(Page page,
                                           @Param("projectId") String projectId);


}