package com.tuozuo.tavern.xinruyi.convert;

import com.tuozuo.tavern.common.protocol.SystemID;
import com.tuozuo.tavern.common.protocol.UserTypeDict;
import com.tuozuo.tavern.xinruyi.dto.PaymentDTO;
import com.tuozuo.tavern.xinruyi.dto.PaymentHistoryDTO;
import com.tuozuo.tavern.xinruyi.dto.ProjectDetailDTO;
import com.tuozuo.tavern.xinruyi.dto.ProjectInfoDTO;
import com.tuozuo.tavern.xinruyi.model.CompanyInfoExt;
import com.tuozuo.tavern.xinruyi.model.ProjectInfo;
import com.tuozuo.tavern.xinruyi.model.ProjectPayment;
import com.tuozuo.tavern.xinruyi.model.ProjectStaff;
import com.tuozuo.tavern.xinruyi.utils.DateUtils;
import com.tuozuo.tavern.xinruyi.utils.UUIDUtil;
import com.tuozuo.tavern.xinruyi.vo.*;
import com.tuuozuo.tavern.authority.spi.vo.UserVO;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/8/30 <br>
 */
public class ModelConverterFactory {

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
        if (projectInfo.getBudget() != null) {
            dto.setBudget(new BigDecimal(projectInfo.getBudget()));
        }
        dto.setStaffNum(new BigDecimal(projectInfo.getProjectMemberCount()));
        dto.setBeginDate(DateUtils.formatDate(projectInfo.getPublishDate(), DateUtils.SIMPLE_8_FORMATTER));
        int days = projectInfo.getPeriod().multiply(new BigDecimal(30)).intValue();
        LocalDate endDate = projectInfo.getPublishDate().plusDays(days);
        dto.setEndDate(DateUtils.formatDate(endDate, DateUtils.SIMPLE_8_FORMATTER));
        dto.setProjectStatus(projectInfo.getStatus());
        dto.setIndustryType(projectInfo.getProjectIndustry());
        dto.setCompanyLogo(projectInfo.getCompanyLogo());
        dto.setProjectDesc(projectInfo.getProjectDesc());
        dto.setProjectStatusDesc(projectInfo.getStatusDesc());
        dto.setIndustryName(projectInfo.getIndustryName());
        return dto;
    }

    public static ProjectInfo addVoToProjectInfo(ProjectAddVO vo, String companyId) {
        ProjectInfo projectInfo = new ProjectInfo();
        projectInfo.setCompanyId(companyId);
        projectInfo.setProjectId(UUIDUtil.randomUUID32());
        projectInfo.setProjectName(vo.getProjectName());
        projectInfo.setProjectIndustry(vo.getIndustryType());
        projectInfo.setPublishDate(DateUtils.parseDate(vo.getReleaseDate(), DateUtils.DEFAULT_SIMPLE_8__FORMATTER));
        projectInfo.setPeriod(vo.getProjectCycle());
        projectInfo.setContractPerson(vo.getContactName());
        projectInfo.setContractPhone(vo.getContact());
        projectInfo.setProjectMemberCount(vo.getStaffNum().intValue());
        projectInfo.setProvince(vo.getProvince());
        projectInfo.setCity(vo.getCity());
        projectInfo.setDistrict(vo.getDistrict());
        projectInfo.setOnSpot(vo.getIsResident());
        projectInfo.setProjectDesc(vo.getDesc());
        if (vo.getBudget() != null) {
            projectInfo.setBudget(vo.getBudget().toPlainString());
        }
        return projectInfo;
    }

    public static ProjectInfo modifyVoToProjectInfo(ProjectModifyVO vo, String companyId, ProjectInfo projectInfo) {
        projectInfo.setCompanyId(companyId);
        projectInfo.setProjectId(vo.getProjectId());
        projectInfo.setProjectName(vo.getProjectName());
        projectInfo.setProjectIndustry(vo.getIndustryType());
        if (StringUtils.isNoneEmpty(vo.getReleaseDate())) {
            projectInfo.setPublishDate(DateUtils.parseDate(vo.getReleaseDate(), DateUtils.DEFAULT_SIMPLE_8__FORMATTER));
        }
        projectInfo.setPeriod(vo.getProjectCycle());
        projectInfo.setContractPerson(vo.getContactName());
        projectInfo.setContractPhone(vo.getContact());
        projectInfo.setProjectMemberCount(vo.getStaffNum().intValue());
        projectInfo.setProvince(vo.getProvince());
        projectInfo.setCity(vo.getCity());
        projectInfo.setDistrict(vo.getDistrict());
        projectInfo.setOnSpot(vo.getIsResident());
        projectInfo.setProjectDesc(vo.getDesc());
        if (vo.getBudget() != null) {
            projectInfo.setBudget(vo.getBudget().toPlainString());
        }
        return projectInfo;
    }

    public static ProjectDetailDTO modelToProjectDetailInfo(ProjectInfo projectInfo) {
        ProjectDetailDTO projectInfoDTO = new ProjectDetailDTO();
        projectInfoDTO.setProjectName(projectInfo.getProjectName());
        projectInfoDTO.setIndustryType(projectInfo.getProjectIndustry());
        projectInfoDTO.setReleaseDate(DateUtils.formatDate(projectInfo.getPublishDate(), DateUtils.SIMPLE_8_FORMATTER));
        projectInfoDTO.setProjectCycle(projectInfo.getPeriod().toPlainString());
        projectInfoDTO.setStaffNum(projectInfo.getProjectMemberCount());
        projectInfoDTO.setProvince(projectInfo.getProvince());
        projectInfoDTO.setCity(projectInfo.getCity());
        projectInfoDTO.setDistrict(projectInfo.getDistrict());
        projectInfoDTO.setIsResident(projectInfo.getOnSpot());
        projectInfoDTO.setContactName(projectInfo.getContractPerson());
        projectInfoDTO.setContact(projectInfo.getContractPhone());
        projectInfoDTO.setProjectFile(projectInfo.getFileMaterial());
        projectInfoDTO.setDesc(projectInfo.getProjectDesc());
        projectInfoDTO.setRemark(projectInfo.getRemark());
        if (StringUtils.isNotEmpty(projectInfo.getBudget())) {
            projectInfoDTO.setBudget(new BigDecimal(projectInfo.getBudget()));
        }
        return projectInfoDTO;
    }

    public static CompanyInfoExt authVOToCompanyInfoExt(CompanyAuthInfoVO vo) {
        CompanyInfoExt ext = new CompanyInfoExt();
        ext.setCompanyId(vo.getCompanyId());
        ext.setLegalPersonName(vo.getBossName());
        ext.setLegalPersonIdentity(vo.getBossId());
        ext.setCompanyBankAccount(vo.getCompanyAccount());
        ext.setCompanyBank(vo.getCompanyAccountBank());
        ext.setCompanyBranchBank(vo.getCompanyAccountBranchBank());
        ext.setContactName(vo.getContactName());
        ext.setContactPhone(vo.getContact());
        return ext;
    }



    public static UserVO authInfoToUserVO(String userId, String customPswd, String privilege) {
        return authInfoToUserVO(userId, customPswd, UserTypeDict.custom,privilege);
    }
    public static UserVO authInfoToUserVO(String userId, String customPswd, String userType,String privilege) {
        UserVO user = new UserVO();
        user.setUserId(userId);
        user.setSystemId(SystemID.SYS_ID_XINRUYI);
        user.setRoleGroup(userType);
        user.setUserPswd(customPswd);
        user.setPrivilege(privilege);
        return user;
    }

    public static UserVO authInfoToUserVO(String userId, String privilege) {
        return authInfoToUserVO(userId, null, privilege);
    }

    public static PaymentDTO modelToPaymentDTO(ProjectPayment projectPayment) {
        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setCompanyId(projectPayment.getCompanyId());
        paymentDTO.setCompanyName(projectPayment.getCompanyName());
        paymentDTO.setProjectId(projectPayment.getProjectId());
        paymentDTO.setProjectName(projectPayment.getProjectName());
        paymentDTO.setTotalWages(projectPayment.getTotalSalary());
        paymentDTO.setReleaseDate(DateUtils.formatDate(projectPayment.getPayDate(), DateUtils.DEFAULT_SIMPLE_8__FORMATTER));
        paymentDTO.setTransferVoucher(projectPayment.getFileVoucher());
        paymentDTO.setPayVoucher(projectPayment.getFilePayCert());
        paymentDTO.setReleaseStatus(projectPayment.getStatus());
        paymentDTO.setPaymentId(projectPayment.getPaymentId());
        paymentDTO.setMonth(projectPayment.getPeriod());
        paymentDTO.setRemark(projectPayment.getRemark());
        return paymentDTO;
    }

    public static PaymentHistoryDTO modelToPaymentHistoryDTO(ProjectPayment projectPayment) {
        PaymentHistoryDTO paymentDTO = new PaymentHistoryDTO();
        paymentDTO.setCompanyId(projectPayment.getCompanyId());
        paymentDTO.setCompanyName(projectPayment.getCompanyName());
        paymentDTO.setProjectId(projectPayment.getProjectId());
        paymentDTO.setProjectName(projectPayment.getProjectName());
        paymentDTO.setTotalWages(projectPayment.getTotalSalary());
        paymentDTO.setReleaseDate(DateUtils.formatDate(projectPayment.getPayDate(), DateUtils.DEFAULT_SIMPLE_8__FORMATTER));
        paymentDTO.setTransferVoucher(projectPayment.getFileVoucher());
        paymentDTO.setPayVoucher(projectPayment.getFilePayCert());
        paymentDTO.setStatus(projectPayment.getStatus());
        paymentDTO.setPaymentId(projectPayment.getPaymentId());
        paymentDTO.setMonth(projectPayment.getPeriod());
        paymentDTO.setRemark(projectPayment.getRemark());
        return paymentDTO;
    }


}
