package com.tuozuo.tavern.corp.assist.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tuozuo.tavern.corp.assist.model.CorporationGroupInfo;

import java.util.List;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2021/1/2 <br>
 */
public interface CorporationGroupInfoService {

    boolean addGroup(String GroupName);

    boolean delGroup(String GroupId);

    boolean modifyGroup(String GroupId, String GroupName);

    IPage<CorporationGroupInfo> queryGroups(int pageNo, int pageSize);

    List<CorporationGroupInfo> queryGroupsByName(String GroupName, int queryCnt);

}
