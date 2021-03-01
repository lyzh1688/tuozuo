package com.tuozuo.tavern.corp.assist.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tuozuo.tavern.corp.assist.model.CorporationClientTagInfo;
import com.tuozuo.tavern.corp.assist.vo.CorporationClientVO;

import java.util.List;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2021/1/2 <br>
 */
public interface CorporationClientInfoService {

    boolean addClient(CorporationClientVO corporationClientVO);

    boolean delClient(String clientId);

    boolean modifyClient(CorporationClientVO corporationClientVO);

    IPage<CorporationClientTagInfo> queryClients(String tagName, String clientName, int pageNo, int pageSize,String corpName);

    boolean bindClientTag(String clientId, List<String> tags);

    List<CorporationClientTagInfo> queryClientsFromApp(String tagName,
                                                        String clientName,
                                                        String clientId,
                                                        String createTime,String corpName);

    CorporationClientTagInfo queryClientDetail(String clientId,String type);
}
