package com.tuozuo.tavern.xinruyi.mapper;

import com.tuozuo.tavern.xinruyi.model.ProjectStaff;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProjectStaffMapper {
    int deleteByPrimaryKey(@Param("projectId") String projectId, @Param("staffId") String staffId);

    int insert(ProjectStaff record);

    ProjectStaff selectByPrimaryKey(@Param("projectId") String projectId, @Param("staffId") String staffId);

    List<ProjectStaff> selectAll();

    int updateByPrimaryKey(ProjectStaff record);
}