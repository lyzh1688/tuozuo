package com.tuozuo.tavern.xinruyi.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tuozuo.tavern.xinruyi.model.*;
import com.tuozuo.tavern.xinruyi.vo.*;

import java.util.List;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/8/29 <br>
 */
public interface ProjectInfoService {

    List<ProjectInfo> queryProjectInfo(String companyId, String projectName, int queryCnt, Boolean all, String roleGroup);

    IPage<ProjectStaffInfo> queryProjectStaffInfo(int pageNo, int pageSize, String companyId, String projectId);

    void addProjectStaff(ProjectStaff projectStaff);

    void modifyProjectStaff(ProjectStaff projectStaff) throws Exception;

    void delProjectStaff(String projectId, String staffId, String companyId) throws Exception;

    IPage<ProjectInfo> queryProjectInfo(ProjectListVo vo, String companyId);

    void addProjectInfo(ProjectAddVO vo, String companyId) throws Exception;

    void modifyProjectInfo(ProjectModifyVO vo, String companyId) throws Exception;

    void endProject(String project,String companyId);

    ProjectInfo queryProjectDetail(String projectId);

    IPage<ProjectEventInfo> queryProjectEvents(ProjectEventVO vo);

    void auditProjectRelease(AuditProjectReleaseVO vo);

    void auditProjectDone(AuditProjectDoneVO vo);

    void auditProjectStaffFired(AuditStaffFiredVO vo) throws Exception;

    void modifyProjectStatusTask();

    boolean isRepeatedApply(String companyId,String projectName);

    boolean isRepeatedAuthApply(String companyId,String projectId,String eventType);

    boolean isRepeatedStaffFiredApply(String companyId,String projectId,String staffId,String eventType);

    List<HotProjectInfo> queryHotProjects();

    List<IndustryProjectInfo> queryIndustryProject(String projectId,
                                                    String publishDate,
                                                    String industryId);
}
