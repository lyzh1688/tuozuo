package com.tuozuo.tavern.xinruyi.mapper;

import com.tuozuo.tavern.xinruyi.model.ProjectPaymentDetail;
import java.util.List;

public interface ProjectPaymentDetailMapper {
    int deleteByPrimaryKey(String paymentId);

    int insert(ProjectPaymentDetail record);

    ProjectPaymentDetail selectByPrimaryKey(String paymentId);

    List<ProjectPaymentDetail> selectAll();

    int updateByPrimaryKey(ProjectPaymentDetail record);
}