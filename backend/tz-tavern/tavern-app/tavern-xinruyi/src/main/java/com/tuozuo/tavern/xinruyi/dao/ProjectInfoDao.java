package com.tuozuo.tavern.xinruyi.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tuozuo.tavern.xinruyi.model.HotProjectInfo;
import com.tuozuo.tavern.xinruyi.model.ProjectInfo;
import com.tuozuo.tavern.xinruyi.model.ProjectStaff;
import com.tuozuo.tavern.xinruyi.model.ProjectStaffInfo;

import java.util.List;
import java.util.Optional;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/8/29 <br>
 */
public interface ProjectInfoDao {

    List<ProjectInfo> selectAllProjectInfo( String projectName);

    List<ProjectInfo> selectAllCustomProjectInfo(String companyId, String projectName);

    List<ProjectInfo> selectProjectInfo(String projectName, int queryCnt);

    List<ProjectInfo> selectCustomProjectInfo( String companyId,String projectName, int queryCnt);

    IPage<ProjectInfo> selectProjectPage(int pageNo, int pageSize, String companyId, String projectId,
                                         String industryType,
                                         String limitBudget,
                                         String upperBudget,
                                         String requirementStatus,
                                         String beginDate,
                                         String endDate
                                         );

    void insertProject(ProjectInfo projectInfo);

    ProjectInfo selectProjectInfo(String projectId);

    void modifyProject(ProjectInfo projectInfo);

    void updateStatus();

    Optional<ProjectInfo> selectProjectInfo(String companyId, String projectName);

    List<HotProjectInfo> selectHotProjects();


}
