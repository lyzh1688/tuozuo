package com.tuozuo.tavern.corp.assist.convert;

import com.tuozuo.tavern.corp.assist.dto.ContractTemplateItemDTO;
import com.tuozuo.tavern.corp.assist.dto.CorporationGroupMember;
import com.tuozuo.tavern.corp.assist.facade.corpwechat.model.ClientInfo;
import com.tuozuo.tavern.corp.assist.facade.corpwechat.model.MemberList;
import com.tuozuo.tavern.corp.assist.model.CorporationClientInfo;
import com.tuozuo.tavern.corp.assist.model.CorporationContractTemplate;
import com.tuozuo.tavern.corp.assist.model.CorporationGroupInfo;
import com.tuozuo.tavern.corp.assist.vo.CorporationClientVO;
import com.tuozuo.tavern.corp.assist.vo.CorporationGroupInfoVO;
import org.mapstruct.Mapper;


/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/8/28 <br>
 */
@Mapper(componentModel = "spring")
public interface ModelMapConverterFactory {

    ContractTemplateItemDTO templateModelToDTO(CorporationContractTemplate template);

    CorporationClientInfo dtoToCorporationClientInfo(CorporationClientVO vo);

    CorporationGroupInfo dtoToCorporationGroupInfo(CorporationGroupInfoVO vo);


    CorporationGroupMember clientInfoToGroupMember(ClientInfo clientInfo);


}
