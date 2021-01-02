package com.tuozuo.tavern.corp.assist.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tuozuo.tavern.corp.assist.model.CorporationClientInfo;
import com.tuozuo.tavern.corp.assist.model.CorporationClientTagInfo;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2021/1/2 <br>
 */
public interface CorporationClientInfoDao {

    boolean insertClient(CorporationClientInfo corporationClientInfo);

    boolean delClient(String clientId);

    boolean updateClient(CorporationClientInfo corporationClientInfo);

    IPage<CorporationClientTagInfo> queryClients(String tagName, String clientName, Page<CorporationClientTagInfo> page);
}
