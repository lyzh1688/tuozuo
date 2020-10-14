package com.tuozuo.tavern.xinruyi.convert;

import com.tuozuo.tavern.xinruyi.dto.*;
import com.tuozuo.tavern.xinruyi.model.*;
import com.tuozuo.tavern.xinruyi.vo.CompanyApplyVO;
import com.tuozuo.tavern.xinruyi.vo.ProjectStaffAddVO;
import com.tuozuo.tavern.xinruyi.vo.StaffInfoVO;
import com.tuozuo.tavern.xinruyi.vo.StaffModifyVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.math.BigDecimal;


/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/8/28 <br>
 */
@Mapper(componentModel = "spring")
public interface ModelMapConverterFactory {

    @Mappings({
            @Mapping(source = "vo.name", target = "staffName"),
            @Mapping(source = "vo.idNo", target = "idNumber"),
            @Mapping(source = "vo.accntBank", target = "bankBranch"),
            @Mapping(source = "companyId", target = "companyId"),
            @Mapping(target = "staffId", expression = "java(com.tuozuo.tavern.xinruyi.utils.UUIDUtil.randomUUID32())"),
            @Mapping(target = "updateDate", expression = "java(java.time.LocalDateTime.now())")
    })
    StaffResourcePool addVoToStaffResourcePool(StaffInfoVO vo, String companyId);

    @Mappings({
            @Mapping(source = "vo.gender", target = "gender"),
            @Mapping(source = "vo.bankCard", target = "bankCard"),
            @Mapping(source = "vo.bank", target = "bank"),
            @Mapping(source = "vo.accntBank", target = "bankBranch"),
            @Mapping(source = "companyId", target = "companyId"),
            @Mapping(source = "staffId", target = "staffId"),
            @Mapping(target = "updateDate", expression = "java(java.time.LocalDateTime.now())")
    })
    StaffResourcePool modifyVoToStaffResourcePool(StaffModifyVO vo, String companyId, String staffId);

    @Mappings({
            @Mapping(source = "businessDict.businessId", target = "id"),
            @Mapping(source = "businessDict.businessName", target = "name")
    })
    BusinessDictDTO modelToBusinessDictDTO(BusinessDict businessDict);

    @Mappings({
            @Mapping(source = "staffId", target = "id"),
            @Mapping(source = "staffName", target = "name"),
            @Mapping(source = "idNumber", target = "idNo"),
            @Mapping(source = "bankBranch", target = "accntBank")
    })
    StaffResourcePoolDTO modelToStaffResourcePoolDTO(StaffResourcePool staffResourcePool);

    @Mappings({
            @Mapping(source = "idNumber", target = "idNo")
    })
    StaffDictDTO staffPoolToBusinessDict(StaffResourcePool pool);

    @Mappings({
            @Mapping(source = "status", target = "authStatus"),
            @Mapping(source = "companyIndustry", target = "industryType")
    })
    CompanyInfoDTO modelToCompanyInfoDTO(CompanyInfo companyInfo);


    @Mappings({
            @Mapping(source = "fileBizLicense", target = "businessLicense"),
            @Mapping(source = "legalPersonName", target = "bossName"),
            @Mapping(source = "legalPersonIdentity", target = "bossId"),
            @Mapping(source = "fileLegalPersonIdcardUp", target = "bossIdPicUp"),
            @Mapping(source = "fileLegalPersonIdcardBack", target = "bossIdPicBack"),
            @Mapping(source = "companyBankAccount", target = "companyAccount"),
            @Mapping(source = "companyBranchBank", target = "companyAccountBranchBank"),
            @Mapping(source = "companyBank", target = "companyAccountBank"),
            @Mapping(source = "contactPhone", target = "contact"),
            @Mapping(source = "companyName", target = "companyName"),
            @Mapping(source = "bankName", target = "bankName"),
            @Mapping(source = "bankBranchName", target = "bankBranchName"),
    })
    CompanyDetailInfoDTO modelToCompanyDetailInfoDTO(CompanyInfoExt companyInfoExt);

    EventInfoDTO modelToEventInfoDTO(EventTodoList eventTodoList);

    EventInfoDTO modelToEventInfoDTO(EventFinishList eventFinishList);

    @Mappings({
            @Mapping(target = "registerId", expression = "java(com.tuozuo.tavern.xinruyi.utils.UUIDUtil.randomUUID32())"),
            @Mapping(target = "companyIndustry", source = "industryType"),

    })
    CompanyInfo voToCompanyInfo(CompanyApplyVO vo);

    @Mappings({
            @Mapping(target = "name", source = "staffName"),

    })
    StaffSalaryDetailDTO modelToStaffSalaryDetail(StaffSalaryInfo staffSalaryInfo);

    StaffSalaryInfoDTO modelToStaffSalaryInfo(StaffSalaryInfo staffSalaryInfo);


    StaffSalaryHistoryDTO modelToStaffSalaryHistoryDTO(StaffSalaryInfo staffSalaryInfo);

    @Mappings({
            @Mapping(target = "industryId", source = "businessId"),
            @Mapping(target = "industryName", source = "businessName"),

    })
    IndustryTypeDTO modelToIndustryTypeDTO(BusinessDict businessDict);


    @Mappings({
            @Mapping(target = "projectStatus", source = "status"),
            @Mapping(target = "projectCycle", source = "period"),

    })
    ProjectExperienceDTO modelToProjectExperienceDTO(ProjectInfo projectInfo);

    @Mappings({
            @Mapping(target = "projectStatus", source = "status"),

    })
    ProjectExperienceDetailDTO modelToProjectExperienceDetailDTO(ProjectInfo projectInfo);

    @Mappings({
            @Mapping(target = "releaseDate", source = "payDate"),
    })
    ProjectExperiencePaymentDTO modelToProjectExperiencePaymentDTO(ProjectPaymentDetail detail);



}
