package com.tuozuo.tavern.shuiruyi.convert;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.tavern.shuiruyi.model.CompanyInfo;
import com.tuozuo.tavern.shuiruyi.dto.BusinessDictDTO;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2019/08/10 <br>
 */
@Mapper(componentModel = "spring")
public interface BusinessConverter {

    @Mapping(source = "companyInfo.companyId", target = "id")
    @Mapping(source = "companyInfo.companyName", target = "name")
    BusinessDictDTO companyInfoToDTO(CompanyInfo companyInfo);

}
