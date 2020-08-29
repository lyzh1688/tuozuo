package com.tuozuo.tavern.xinruyi.dao.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
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
    public List<ProjectInfo> selectAllProjectInfo(String companyId, String projectName) {
        return this.projectInfoMapper.selectList(Wrappers.<ProjectInfo>query()
                .lambda()
                .like(ProjectInfo::getProjectName, projectName)
                .eq(ProjectInfo::getCompanyId, companyId)
                .orderByAsc(ProjectInfo::getProjectName));
    }

    @Override
    public List<ProjectInfo> selectProjectInfo(String companyId, String projectName, int queryCnt) {
        return this.projectInfoMapper.selectList(Wrappers.<ProjectInfo>query()
                .lambda()
                .like(ProjectInfo::getProjectName, projectName)
                .eq(ProjectInfo::getCompanyId, companyId)
                .orderByAsc(ProjectInfo::getProjectName)
                .last("limit " + queryCnt));
    }



}
