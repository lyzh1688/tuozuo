package com.tuozuo.tavern.xinruyi.dao.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tuozuo.tavern.xinruyi.dao.ProjectStaffInfoDao;
import com.tuozuo.tavern.xinruyi.mapper.ProjectStaffMapper;
import com.tuozuo.tavern.xinruyi.model.ProjectInfo;
import com.tuozuo.tavern.xinruyi.model.ProjectStaff;
import com.tuozuo.tavern.xinruyi.model.ProjectStaffInfo;
import com.tuozuo.tavern.xinruyi.model.StaffBasicInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/8/29 <br>
 */
@Repository
public class ProjectStaffInfoDaoImpl implements ProjectStaffInfoDao {

    @Autowired
    private ProjectStaffMapper projectStaffMapper;


    @Override
    public IPage<ProjectStaffInfo> selectProjectStaffInfo(int pageNo, int pageSize, String companyId, String projectId) {
        Page page = new Page(pageNo, pageSize);
        return this.projectStaffMapper.selectProjectStaffInfo(page, companyId, projectId);
    }

    @Override
    public void insertProjectStaff(ProjectStaff projectStaff) {
        this.projectStaffMapper.insert(projectStaff);
    }

    @Override
    public void updateProjectStaff(ProjectStaff projectStaff) {
        this.projectStaffMapper.update(projectStaff, Wrappers.<ProjectStaff>query()
                .lambda()
                .eq(ProjectStaff::getStaffId, projectStaff.getStaffId())
                .eq(ProjectStaff::getProjectId, projectStaff.getProjectId()));
    }

    @Override
    public void delProjectStaff(ProjectStaff projectStaff) {
        this.projectStaffMapper.update(projectStaff, Wrappers.<ProjectStaff>query()
                .lambda()
                .eq(ProjectStaff::getStaffId, projectStaff.getStaffId())
                .eq(ProjectStaff::getProjectId, projectStaff.getProjectId()));
    }

    @Override
    public ProjectStaff select(String projectId, String staffId) {
        return this.projectStaffMapper.selectOne(Wrappers.<ProjectStaff>query()
                .lambda()
                .eq(ProjectStaff::getStaffId, staffId)
                .eq(ProjectStaff::getProjectId, projectId));
    }

    @Override
    public StaffBasicInfo selectStaff(String projectId, String staffId) {
        return this.projectStaffMapper.selectStaff(projectId, staffId);
    }
}
