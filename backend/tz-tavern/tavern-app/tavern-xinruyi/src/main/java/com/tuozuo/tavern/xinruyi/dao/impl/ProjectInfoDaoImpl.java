package com.tuozuo.tavern.xinruyi.dao.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tuozuo.tavern.xinruyi.dao.ProjectInfoDao;
import com.tuozuo.tavern.xinruyi.mapper.HotProjectInfoMapper;
import com.tuozuo.tavern.xinruyi.mapper.ProjectInfoMapper;
import com.tuozuo.tavern.xinruyi.model.HotProjectInfo;
import com.tuozuo.tavern.xinruyi.model.IndustryProjectInfo;
import com.tuozuo.tavern.xinruyi.model.ProjectInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/8/29 <br>
 */
@Repository
public class ProjectInfoDaoImpl implements ProjectInfoDao {

    @Autowired
    private ProjectInfoMapper projectInfoMapper;
    @Autowired
    private HotProjectInfoMapper hotProjectInfoMapper;

    @Override
    public List<ProjectInfo> selectAllProjectInfo(String projectName) {
        return this.projectInfoMapper.selectList(Wrappers.<ProjectInfo>query()
                .lambda()
                .like(StringUtils.isNoneEmpty(projectName), ProjectInfo::getProjectName, projectName)
                .orderByAsc(ProjectInfo::getProjectName));
    }

    @Override
    public List<ProjectInfo> selectAllCustomProjectInfo(String companyId, String projectName) {
        return this.projectInfoMapper.selectList(Wrappers.<ProjectInfo>query()
                .lambda()
                .like(StringUtils.isNoneEmpty(projectName), ProjectInfo::getProjectName, projectName)
                .eq(ProjectInfo::getCompanyId, companyId)
                .orderByAsc(ProjectInfo::getProjectName));
    }

    @Override
    public List<ProjectInfo> selectProjectInfo(String projectName, int queryCnt) {
        return this.projectInfoMapper.selectList(Wrappers.<ProjectInfo>query()
                .lambda()
                .like(StringUtils.isNoneEmpty(projectName), ProjectInfo::getProjectName, projectName)
                .orderByAsc(ProjectInfo::getProjectName)
                .last("limit " + queryCnt));
    }

    @Override
    public List<ProjectInfo> selectCustomProjectInfo(String companyId, String projectName, int queryCnt) {
        return this.projectInfoMapper.selectList(Wrappers.<ProjectInfo>query()
                .lambda()
                .like(StringUtils.isNoneEmpty(projectName), ProjectInfo::getProjectName, projectName)
                .eq(ProjectInfo::getCompanyId, companyId)
                .orderByAsc(ProjectInfo::getProjectName)
                .last("limit " + queryCnt));
    }

    @Override
    public IPage<ProjectInfo> selectProjectPage(int pageNo, int pageSize, String companyId, String projectId, String industryType, String limitBudget, String upperBudget, String requirementStatus, String beginDate,
                                                String endDate) {
        Page page = new Page(pageNo, pageSize);
        return this.projectInfoMapper.selectProjectPage(page, companyId, projectId, industryType, limitBudget, upperBudget, requirementStatus, beginDate,
                endDate);
    }

    @Override
    public void insertProject(ProjectInfo projectInfo) {
        this.projectInfoMapper.insert(projectInfo);
    }

    @Override
    public ProjectInfo selectProjectInfo(String projectId) {
        return this.projectInfoMapper.selectByProjectId(projectId);
    }

    @Override
    public void modifyProject(ProjectInfo projectInfo) {
        this.projectInfoMapper.update(projectInfo, Wrappers.<ProjectInfo>query()
                .lambda()
                .eq(ProjectInfo::getProjectId, projectInfo.getProjectId()));
    }

    @Override
    public void updateStatus() {
        this.projectInfoMapper.updateStatus();
    }

    @Override
    public Optional<ProjectInfo> selectProjectInfo(String companyId, String projectName) {
        return Optional.ofNullable(this.projectInfoMapper.selectOne(Wrappers.<ProjectInfo>query()
                .lambda()
                .eq(ProjectInfo::getProjectName, projectName)
                .eq(ProjectInfo::getCompanyId, companyId)));

    }

    @Override
    public List<HotProjectInfo> selectHotProjects() {
        return this.hotProjectInfoMapper.selectAll();
    }

    @Override
    public List<IndustryProjectInfo> selectIndustryProjectById(String projectId, String publishDate, String industryId) {
        return this.projectInfoMapper.selectIndustryProjectPage(projectId, publishDate, industryId);
    }

    @Override
    public List<IndustryProjectInfo> selectIndustryProjectByName(String projectName, String publishDate, String projectId) {
        return this.projectInfoMapper.selectIndustryProject(projectName, publishDate,projectId);
    }

    @Override
    public List<ProjectInfo> selectCurProjects(String projectId, String publishDate, String registerId) {
        return this.projectInfoMapper.selectCurProjects(projectId, publishDate, registerId);
    }

    @Override
    public List<ProjectInfo> selectFinishedProjects(String projectId, String publishDate, String registerId) {
        return this.projectInfoMapper.selectFinishedProjects(projectId, publishDate, registerId);
    }


}
