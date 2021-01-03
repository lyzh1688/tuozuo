package com.tuozuo.tavern.corp.assist.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tuozuo.tavern.corp.assist.model.CorporationGroupClientInfo;
import com.tuozuo.tavern.corp.assist.model.CorporationGroupInfo;
import com.tuozuo.tavern.corp.assist.vo.CorporationGroupInfoVO;

import java.util.List;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2021/1/2 <br>
 */
public interface CorporationGroupInfoService {

    boolean addGroup(CorporationGroupInfoVO groupInfoVO);

    boolean delGroup(String groupId);

    boolean modifyGroup(CorporationGroupInfoVO groupInfoVO);

    IPage<CorporationGroupClientInfo> queryGroups(String tagName, String groupName, int pageNo, int pageSize);

    CorporationGroupClientInfo queryGroupDetail(String groupId);

    boolean bindGroupClientRel(String groupId,List<String> clientId);


}
