package com.tuozuo.tavern.corp.assist.convert;

import com.tuozuo.tavern.corp.assist.dto.ContractTemplateItemDTO;
import com.tuozuo.tavern.corp.assist.dto.CorporationGroupMember;
import com.tuozuo.tavern.corp.assist.facade.corpwechat.model.ClientInfo;
import com.tuozuo.tavern.corp.assist.facade.corpwechat.model.ClientInfoDetail;
import com.tuozuo.tavern.corp.assist.facade.corpwechat.model.MemberList;
import com.tuozuo.tavern.corp.assist.model.*;
import com.tuozuo.tavern.corp.assist.vo.CorporationBusinessDictVO;
import com.tuozuo.tavern.corp.assist.vo.CorporationClientVO;
import com.tuozuo.tavern.corp.assist.vo.CorporationGroupInfoVO;
import com.tuozuo.tavern.corp.assist.vo.CorporationInfoVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;


/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/8/28 <br>
 */
@Mapper(componentModel = "spring")
public interface ModelMapConverterFactory {

    ContractTemplateItemDTO templateModelToDTO(CorporationContractTemplate template);

    CorporationClientInfo dtoToCorporationClientInfo(CorporationClientVO vo);

    CorporationGroupInfo dtoToCorporationGroupInfo(CorporationGroupInfoVO vo);

    @Mappings({
            @Mapping(source = "externalUserId", target = "userId"),})
    CorporationGroupMember clientInfoToGroupMember(ClientInfoDetail clientInfoDetail);

    @Mappings({
            @Mapping(source = "registerDate", target = "registerDate", dateFormat = "yyyyMMdd"),})
    CorporationInfo voToCorporationInfo(CorporationInfoVO vo);


    BusinessDict voToBusinessDict(CorporationBusinessDictVO vo);


}
