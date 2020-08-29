package com.tuozuo.tavern.xinruyi.convert;

import com.tuozuo.tavern.xinruyi.dto.BusinessDictDTO;
import com.tuozuo.tavern.xinruyi.model.BusinessDict;
import com.tuozuo.tavern.xinruyi.model.StaffResourcePool;
import com.tuozuo.tavern.xinruyi.vo.StaffInfoVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;


/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/8/28 <br>
 */
@Mapper
public interface ModelConverter {

    @Mappings({
            @Mapping(source = "vo.name", target = "staffName"),
            @Mapping(source = "vo.idNo", target = "idNumber"),
            @Mapping(source = "vo.accntBank", target = "bankBranch"),
            @Mapping(source = "1", target = "isValid"),
            @Mapping(source = "companyId", target = "companyId"),
            @Mapping(source = "java.time.LocalDateTime.now()", target = "updateDate")
    })
    StaffResourcePool voToStaffResourcePool(StaffInfoVO vo, String companyId);

    @Mappings({
            @Mapping(source = "businessDict.businessId", target = "id"),
            @Mapping(source = "businessDict.businessName", target = "name")
    })
    BusinessDictDTO modelToBusinessDictDTO(BusinessDict businessDict);


}
