package com.tuozuo.tavern.xinruyi.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tuozuo.tavern.xinruyi.model.ProjectInfo;
import com.tuozuo.tavern.xinruyi.model.ProjectStaff;
import com.tuozuo.tavern.xinruyi.model.ProjectStaffInfo;
import com.tuozuo.tavern.xinruyi.vo.ProjectAddVO;
import com.tuozuo.tavern.xinruyi.vo.ProjectListVo;
import com.tuozuo.tavern.xinruyi.vo.ProjectModifyVO;

import java.util.List;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/8/29 <br>
 */
public interface ProjectInfoService {

    List<ProjectInfo> queryProjectInfo(String companyId, String projectName, int queryCnt, Boolean all,String roleGroup);

    IPage<ProjectStaffInfo> queryProjectStaffInfo(int pageNo, int pageSize, String companyId, String projectId);

    void addProjectStaff(ProjectStaff projectStaff);

    void modifyProjectStaff(ProjectStaff projectStaff) throws Exception;

    void delProjectStaff(String projectId, String staffId) throws Exception;

    IPage<ProjectInfo> queryProjectInfo(ProjectListVo vo,String companyId);

    void addProjectInfo(ProjectAddVO vo, String companyId) throws Exception;

    void modifyProjectInfo(ProjectModifyVO vo, String companyId) throws Exception;

    void modifyProjectStatus(String  status, String project);

    ProjectInfo queryProjectDetail(String projectId);

}
