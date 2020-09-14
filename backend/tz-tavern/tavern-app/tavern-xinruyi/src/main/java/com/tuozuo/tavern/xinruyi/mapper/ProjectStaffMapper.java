package com.tuozuo.tavern.xinruyi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tuozuo.tavern.xinruyi.model.ProjectStaff;
import java.util.List;

import com.tuozuo.tavern.xinruyi.model.ProjectStaffInfo;
import com.tuozuo.tavern.xinruyi.model.StaffBasicInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ProjectStaffMapper extends BaseMapper<ProjectStaff> {
    IPage<ProjectStaffInfo> selectProjectStaffInfo(Page page, @Param("companyId") String companyId, @Param("projectId") String projectId);

    StaffBasicInfo selectStaff(@Param("projectId") String projectId,@Param("staffId")String staffId);

}