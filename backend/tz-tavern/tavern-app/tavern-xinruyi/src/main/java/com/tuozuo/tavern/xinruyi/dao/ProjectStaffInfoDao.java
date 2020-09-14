package com.tuozuo.tavern.xinruyi.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tuozuo.tavern.xinruyi.model.ProjectStaff;
import com.tuozuo.tavern.xinruyi.model.ProjectStaffInfo;
import com.tuozuo.tavern.xinruyi.model.StaffBasicInfo;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/8/29 <br>
 */
public interface ProjectStaffInfoDao {
    IPage<ProjectStaffInfo> selectProjectStaffInfo(int pageNo, int pageSize, String companyId, String projectId);

    void insertProjectStaff(ProjectStaff projectStaff);

    void updateProjectStaff(ProjectStaff projectStaff);

    void delProjectStaff(ProjectStaff projectStaff);

    ProjectStaff select(String projectId,String staffId);

    StaffBasicInfo selectStaff(String projectId,String staffId);

}
