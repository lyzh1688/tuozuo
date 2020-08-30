package com.tuozuo.tavern.xinruyi.convert;

import com.tuozuo.tavern.xinruyi.model.ProjectStaff;
import com.tuozuo.tavern.xinruyi.utils.DateUtils;
import com.tuozuo.tavern.xinruyi.vo.ProjectStaffAddVO;
import com.tuozuo.tavern.xinruyi.vo.ProjectStaffModifyVO;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/8/30 <br>
 */
public class ModelConverter {

   public static ProjectStaff modifyVoToProjectStaff(ProjectStaffAddVO vo, String projectId){
       ProjectStaff projectStaff = new ProjectStaff();
       projectStaff.setProjectId(projectId);
       projectStaff.setStaffId(vo.getStaffId());
       projectStaff.setEnterDate(DateUtils.parseDate(vo.getEnterDate(),DateUtils.DEFAULT_SIMPLE_8__FORMATTER));
       projectStaff.setQuitDate(DateUtils.parseDate(vo.getQuitDate(),DateUtils.DEFAULT_SIMPLE_8__FORMATTER));
       projectStaff.setSalary(vo.getSalary());
       projectStaff.setRemark(vo.getRemark());
        return projectStaff;
    }

    public static ProjectStaff modifyVoToProjectStaff(ProjectStaffModifyVO vo, String staffId){
        ProjectStaff projectStaff = new ProjectStaff();
        projectStaff.setProjectId(vo.getProjectId());
        projectStaff.setStaffId(staffId);
        projectStaff.setQuitDate(DateUtils.parseDate(vo.getQuitDate(),DateUtils.DEFAULT_SIMPLE_8__FORMATTER));
        projectStaff.setSalary(vo.getSalary());
        projectStaff.setRemark(vo.getRemark());
        return projectStaff;
    }

}
