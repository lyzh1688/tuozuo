package com.tuozuo.tavern.xinruyi.dao.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tuozuo.tavern.xinruyi.dao.ProjectInfoDao;
import com.tuozuo.tavern.xinruyi.mapper.ProjectInfoMapper;
import com.tuozuo.tavern.xinruyi.model.ProjectInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/8/29 <br>
 */
@Repository
public class ProjectInfoDaoImpl implements ProjectInfoDao {

    @Autowired
    private ProjectInfoMapper projectInfoMapper;

    @Override
    public List<ProjectInfo> selectAllProjectInfo(String projectName) {
        return this.projectInfoMapper.selectList(Wrappers.<ProjectInfo>query()
                .lambda()
                .like(projectName != null,ProjectInfo::getProjectName, projectName)
                .orderByAsc(ProjectInfo::getProjectName));
    }

    @Override
    public List<ProjectInfo> selectAllCustomProjectInfo(String companyId, String projectName) {
        return this.projectInfoMapper.selectList(Wrappers.<ProjectInfo>query()
                .lambda()
                .like(projectName != null,ProjectInfo::getProjectName, projectName)
                .eq(ProjectInfo::getCompanyId, companyId)
                .orderByAsc(ProjectInfo::getProjectName));
    }

    @Override
    public List<ProjectInfo> selectProjectInfo(String projectName, int queryCnt) {
        return this.projectInfoMapper.selectList(Wrappers.<ProjectInfo>query()
                .lambda()
                .like(projectName != null,ProjectInfo::getProjectName, projectName)
                .orderByAsc(ProjectInfo::getProjectName)
                .last("limit " + queryCnt));
    }

    @Override
    public List<ProjectInfo> selectCustomProjectInfo(String companyId, String projectName, int queryCnt) {
        return this.projectInfoMapper.selectList(Wrappers.<ProjectInfo>query()
                .lambda()
                .like(projectName != null,ProjectInfo::getProjectName, projectName)
                .eq(ProjectInfo::getCompanyId, companyId)
                .orderByAsc(ProjectInfo::getProjectName)
                .last("limit " + queryCnt));
    }

    @Override
    public IPage<ProjectInfo> selectProjectPage(int pageNo, int pageSize, String companyId, String projectId, String industryType, String limitBudget, String upperBudget, String requirementStatus) {
        Page page = new Page(pageNo, pageSize);
        return this.projectInfoMapper.selectProjectPage(page, companyId, projectId, industryType, limitBudget, upperBudget, requirementStatus);
    }

    @Override
    public void insertProject(ProjectInfo projectInfo) {
        this.projectInfoMapper.insert(projectInfo);
    }

    @Override
    public ProjectInfo selectProjectInfo(String projectId) {
        return this.projectInfoMapper.selectById(projectId);
    }

    @Override
    public void modifyProject(ProjectInfo projectInfo) {
        this.projectInfoMapper.update(projectInfo,Wrappers.<ProjectInfo>query()
                .lambda()
                .eq(ProjectInfo::getProjectId, projectInfo.getProjectId()));
    }


}
