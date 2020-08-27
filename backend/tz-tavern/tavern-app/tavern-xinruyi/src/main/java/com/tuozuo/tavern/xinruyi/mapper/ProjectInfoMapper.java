package com.tuozuo.tavern.xinruyi.mapper;

import com.tuozuo.tavern.xinruyi.model.ProjectInfo;
import java.util.List;

public interface ProjectInfoMapper {
    int deleteByPrimaryKey(String projectId);

    int insert(ProjectInfo record);

    ProjectInfo selectByPrimaryKey(String projectId);

    List<ProjectInfo> selectAll();

    int updateByPrimaryKey(ProjectInfo record);
}