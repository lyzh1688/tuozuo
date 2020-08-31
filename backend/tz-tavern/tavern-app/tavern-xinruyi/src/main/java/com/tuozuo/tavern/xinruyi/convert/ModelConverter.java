package com.tuozuo.tavern.xinruyi.convert;

import com.tuozuo.tavern.xinruyi.dto.ProjectInfoDTO;
import com.tuozuo.tavern.xinruyi.model.ProjectInfo;
import com.tuozuo.tavern.xinruyi.model.ProjectStaff;
import com.tuozuo.tavern.xinruyi.utils.DateUtils;
import com.tuozuo.tavern.xinruyi.vo.ProjectStaffAddVO;
import com.tuozuo.tavern.xinruyi.vo.ProjectStaffModifyVO;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/8/30 <br>
 */
public class ModelConverter {

    public static ProjectStaff modifyVoToProjectStaff(ProjectStaffAddVO vo, String projectId) {
        ProjectStaff projectStaff = new ProjectStaff();
        projectStaff.setProjectId(projectId);
        projectStaff.setStaffId(vo.getStaffId());
        projectStaff.setEnterDate(DateUtils.parseDate(vo.getEnterDate(), DateUtils.DEFAULT_SIMPLE_8__FORMATTER));
        projectStaff.setQuitDate(DateUtils.parseDate(vo.getQuitDate(), DateUtils.DEFAULT_SIMPLE_8__FORMATTER));
        projectStaff.setSalary(vo.getSalary());
        projectStaff.setRemark(vo.getRemark());
        return projectStaff;
    }

    public static ProjectStaff modifyVoToProjectStaff(ProjectStaffModifyVO vo, String staffId) {
        ProjectStaff projectStaff = new ProjectStaff();
        projectStaff.setProjectId(vo.getProjectId());
        projectStaff.setStaffId(staffId);
        projectStaff.setQuitDate(DateUtils.parseDate(vo.getQuitDate(), DateUtils.DEFAULT_SIMPLE_8__FORMATTER));
        projectStaff.setSalary(vo.getSalary());
        projectStaff.setRemark(vo.getRemark());
        return projectStaff;
    }

    public static ProjectInfoDTO modelToProjectInfoDTO(ProjectInfo projectInfo) {
        ProjectInfoDTO dto = new ProjectInfoDTO();
        dto.setProjectId(projectInfo.getProjectId());
        dto.setCompanyName(projectInfo.getCompanyName());
        dto.setProjectName(projectInfo.getProjectName());
        dto.setProjectCycle(projectInfo.getPeriod().toPlainString());
        dto.setBudget(new BigDecimal(projectInfo.getBudget()));
        dto.setStaffNum(new BigDecimal(projectInfo.getProjectMemberCount()));
        dto.setBeginDate(DateUtils.formatDate(projectInfo.getPublishDate(), DateUtils.DEFAULT_DATE_FORMATTER));
        int days = projectInfo.getPeriod().multiply(new BigDecimal(30)).intValue();
        LocalDate endDate = projectInfo.getPublishDate().plusDays(days);
        dto.setEndDate(DateUtils.formatDate(endDate, DateUtils.DEFAULT_DATE_FORMATTER));
        dto.setProjectStatus(projectInfo.getStatus());
        return dto;
    }


}
