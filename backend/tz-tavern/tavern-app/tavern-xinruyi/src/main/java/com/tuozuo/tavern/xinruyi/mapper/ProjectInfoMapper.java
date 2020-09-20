package com.tuozuo.tavern.xinruyi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tuozuo.tavern.xinruyi.model.ProjectInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ProjectInfoMapper extends BaseMapper<ProjectInfo> {
    IPage<ProjectInfo> selectProjectPage(Page page,
                                         @Param("companyId") String companyId,
                                         @Param("projectId") String projectId,
                                         @Param("industryType") String industryType,
                                         @Param("downLimitBudget") String downLimitBudget,
                                         @Param("upperLimitBudget") String upperLimitBudget,
                                         @Param("requirementStatus") String requirementStatus,
                                         @Param("beginDate") String beginDate,
                                         @Param("endDate") String endDate);

    void updateStatus();

    ProjectInfo selectByProjectId(@Param("projectId") String projectId);

}