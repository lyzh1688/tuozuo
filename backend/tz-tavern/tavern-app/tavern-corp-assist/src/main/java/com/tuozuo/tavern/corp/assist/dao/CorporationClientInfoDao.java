package com.tuozuo.tavern.corp.assist.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tuozuo.tavern.corp.assist.model.CorporationClientInfo;
import com.tuozuo.tavern.corp.assist.model.CorporationClientTagInfo;

import java.util.List;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2021/1/2 <br>
 */
public interface CorporationClientInfoDao {

    boolean insertClient(CorporationClientInfo corporationClientInfo);

    boolean delClient(CorporationClientInfo corporationClientInfo);

    boolean updateClient(CorporationClientInfo corporationClientInfo);

    IPage<CorporationClientTagInfo> selectClients(String tagName, String clientName, Page<CorporationClientTagInfo> page);

    int selectClientsCnt(String tagName, String clientName);

    List<CorporationClientTagInfo> selectClientsFromApp(String tagName,
                                                        String clientName,
                                                        String clientId,
                                                        String createTime);
}
