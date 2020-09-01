package com.tuozuo.tavern.xinruyi.convert;

import com.tuozuo.tavern.xinruyi.dto.BusinessDictDTO;
import com.tuozuo.tavern.xinruyi.dto.ProjectInfoDTO;
import com.tuozuo.tavern.xinruyi.dto.StaffResourcePoolDTO;
import com.tuozuo.tavern.xinruyi.model.BusinessDict;
import com.tuozuo.tavern.xinruyi.model.ProjectInfo;
import com.tuozuo.tavern.xinruyi.model.ProjectStaff;
import com.tuozuo.tavern.xinruyi.model.StaffResourcePool;
import com.tuozuo.tavern.xinruyi.vo.ProjectStaffAddVO;
import com.tuozuo.tavern.xinruyi.vo.StaffInfoVO;
import com.tuozuo.tavern.xinruyi.vo.StaffModifyVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;


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
            @Mapping(source = "staffId", target = "id"),
            @Mapping(source = "staffName", target = "name")
    })
    BusinessDictDTO staffPoolToBusinessDict(StaffResourcePool pool);




}
