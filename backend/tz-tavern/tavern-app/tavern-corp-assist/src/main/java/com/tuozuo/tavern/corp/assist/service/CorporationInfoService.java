package com.tuozuo.tavern.corp.assist.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tuozuo.tavern.corp.assist.model.CorporationGroupClientInfo;
import com.tuozuo.tavern.corp.assist.vo.CorporationGroupInfoVO;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2021/1/5 <br>
 */
public interface CorporationInfoService {

    boolean addCorporation(CorporationGroupInfoVO groupInfoVO);

    boolean delCorporation(String groupId) throws Exception;

    boolean modifyCorporation(CorporationGroupInfoVO groupInfoVO);

    IPage<CorporationGroupClientInfo> queryCorporations(String tagName, String groupName, int pageNo, int pageSize);

    CorporationGroupClientInfo queryCorporationDetail(String groupId);
}
