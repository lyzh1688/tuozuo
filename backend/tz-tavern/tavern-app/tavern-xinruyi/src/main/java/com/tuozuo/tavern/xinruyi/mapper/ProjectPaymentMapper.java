package com.tuozuo.tavern.xinruyi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tuozuo.tavern.xinruyi.model.ProjectPayment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ProjectPaymentMapper extends BaseMapper<ProjectPayment> {
    IPage<ProjectPayment> selectByPage(Page page,
                                       @Param("companyId") String companyId,
                                       @Param("projectId") String projectId,
                                       @Param("status") String status);

    IPage<ProjectPayment> selectHistoryByPage(Page page,
                                              @Param("companyId") String companyId,
                                              @Param("projectId") String projectId,
                                              @Param("beginMonth") String beginMonth,
                                              @Param("endMonth") String endMonth);
}