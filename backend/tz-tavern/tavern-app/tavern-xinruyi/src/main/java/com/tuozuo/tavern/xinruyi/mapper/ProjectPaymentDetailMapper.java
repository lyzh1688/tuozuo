package com.tuozuo.tavern.xinruyi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tuozuo.tavern.xinruyi.model.ProjectPaymentDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProjectPaymentDetailMapper extends BaseMapper<ProjectPaymentDetail> {

    int insertInitial(@Param("companyId")String companyId,
                      @Param("month")String month,
                      @Param("payDate")String payDate,
                      @Param("paymentId")String paymentId,
                      @Param("projectId")String projectId);

}