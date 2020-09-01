package com.tuozuo.tavern.xinruyi.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tuozuo.tavern.xinruyi.model.ProjectInfo;
import com.tuozuo.tavern.xinruyi.model.ProjectStaff;
import com.tuozuo.tavern.xinruyi.model.ProjectStaffInfo;

import java.util.List;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/8/29 <br>
 */
public interface ProjectInfoDao {

    List<ProjectInfo> selectAllProjectInfo(String companyId, String projectName);

    List<ProjectInfo> selectProjectInfo(String companyId, String projectName, int queryCnt);

    IPage<ProjectInfo> selectProjectPage(int pageNo, int pageSize, String projectId,
                                         String industryType,
                                         String limitBudget,
                                         String upperBudget,
                                         String requirementStatus);

    void insertProject(ProjectInfo projectInfo);

    ProjectInfo selectProjectInfo(String projectId);

    void modifyProject(ProjectInfo projectInfo);


}
