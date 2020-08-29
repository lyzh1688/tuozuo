package com.tuozuo.tavern.xinruyi.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tuozuo.tavern.xinruyi.model.ProjectStaff;
import com.tuozuo.tavern.xinruyi.model.ProjectStaffInfo;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/8/29 <br>
 */
public interface ProjectStaffInfoDao {
    IPage<ProjectStaffInfo> selectProjectStaffInfo(int pageNo, int pageSize, String companyId, String projectId);

    void insertProjectStaff(ProjectStaff projectStaff);

    void updateProjectStaff(ProjectStaff projectStaff);

}
