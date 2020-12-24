package com.tuozuo.tavern.corp.assist.convert;

import com.tuozuo.tavern.corp.assist.dto.ContractTemplateItemDTO;
import com.tuozuo.tavern.corp.assist.model.CompanyContractTemplate;
import org.mapstruct.Mapper;


/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/8/28 <br>
 */
@Mapper(componentModel = "spring")
public interface ModelMapConverterFactory {

    ContractTemplateItemDTO templateModelToDTO(CompanyContractTemplate template);


}
