package com.tuozuo.tavern.xinruyi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tuozuo.tavern.xinruyi.model.IndustryProjectInfo;
import com.tuozuo.tavern.xinruyi.model.ProjectInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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


    List<IndustryProjectInfo> selectIndustryProjectPage(@Param("projectId") String projectId,
                                                        @Param("publishDate") String publishDate,
                                                        @Param("industryId") String industryId);

    List<IndustryProjectInfo> selectIndustryProject(@Param("projectName") String projectName,
                                                    @Param("queryCnt") int queryCnt);

    List<ProjectInfo> selectCurProjects(@Param("projectId") String projectId,
                                        @Param("publishDate") String publishDate,
                                        @Param("registerId") String registerId);

    List<ProjectInfo> selectFinishedProjects(@Param("projectId") String projectId,
                                             @Param("publishDate") String publishDate,
                                             @Param("registerId") String registerId);


}