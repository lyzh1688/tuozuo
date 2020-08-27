package com.tuozuo.tavern.xinruyi.mapper;

import com.tuozuo.tavern.xinruyi.model.ProjectPayment;
import java.util.List;

public interface ProjectPaymentMapper {
    int deleteByPrimaryKey(String paymentId);

    int insert(ProjectPayment record);

    ProjectPayment selectByPrimaryKey(String paymentId);

    List<ProjectPayment> selectAll();

    int updateByPrimaryKey(ProjectPayment record);
}