package com.tuozuo.tavern.xinruyi.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tuozuo.tavern.xinruyi.dao.ProjectInfoDao;
import com.tuozuo.tavern.xinruyi.dao.ProjectStaffInfoDao;
import com.tuozuo.tavern.xinruyi.model.ProjectInfo;
import com.tuozuo.tavern.xinruyi.model.ProjectStaff;
import com.tuozuo.tavern.xinruyi.model.ProjectStaffInfo;
import com.tuozuo.tavern.xinruyi.service.ProjectInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/8/29 <br>
 */
@Service
public class ProjectInfoServiceImpl implements ProjectInfoService {
    @Autowired
    private ProjectInfoDao projectInfoDao;
    @Autowired
    private ProjectStaffInfoDao projectStaffInfoDao;


    @Override
    public List<ProjectInfo> queryProjectInfo(String companyId, String projectName, int queryCnt, Boolean all) {
        if(all){
            return this.projectInfoDao.selectAllProjectInfo(companyId, projectName);
        }else {
            return this.projectInfoDao.selectProjectInfo(companyId, projectName, queryCnt);
        }
    }

    @Override
    public IPage<ProjectStaffInfo> queryProjectStaffInfo(int pageNo, int pageSize, String companyId, String projectId) {
        return this.projectStaffInfoDao.selectProjectStaffInfo(pageNo, pageSize, companyId, projectId);
    }

    @Override
    public void addProjectStaff(ProjectStaff projectStaff) {
        this.projectStaffInfoDao.insertProjectStaff(projectStaff);
    }

    @Override
    public void modifyProjectStaff(ProjectStaff projectStaff) {
        this.projectStaffInfoDao.updateProjectStaff(projectStaff);
    }
}
