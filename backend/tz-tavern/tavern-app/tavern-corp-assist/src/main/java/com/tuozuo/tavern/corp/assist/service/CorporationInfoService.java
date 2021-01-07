package com.tuozuo.tavern.corp.assist.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tuozuo.tavern.corp.assist.model.CorporationInfo;
import com.tuozuo.tavern.corp.assist.vo.CorporationInfoVO;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2021/1/5 <br>
 */
public interface CorporationInfoService {

    boolean addCorporation(CorporationInfoVO corporationInfoVO);

    boolean delCorporation(String corpId) throws Exception;

    boolean modifyCorporation(CorporationInfoVO corporationInfoVO);

    IPage<CorporationInfo> queryCorporations(String corpName, String clientName, int pageNo, int pageSize);

    CorporationInfo queryCorporationDetail(String corpId);
}
