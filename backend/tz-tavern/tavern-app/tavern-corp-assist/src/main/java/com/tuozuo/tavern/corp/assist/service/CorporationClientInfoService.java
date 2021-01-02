package com.tuozuo.tavern.corp.assist.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tuozuo.tavern.corp.assist.model.CorporationClientInfo;
import com.tuozuo.tavern.corp.assist.model.CorporationClientTagInfo;
import com.tuozuo.tavern.corp.assist.vo.CorporationClientVO;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2021/1/2 <br>
 */
public interface CorporationClientInfoService {

    boolean addClient(CorporationClientVO corporationClientVO);

    boolean delClient(String clientId);

    boolean modifyClient(CorporationClientVO corporationClientVO);

    IPage<CorporationClientTagInfo> queryClients(String tagName, String clientName, int pageNo, int pageSize);
}
