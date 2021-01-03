package com.tuozuo.tavern.corp.assist.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tuozuo.tavern.corp.assist.model.CorporationGroupClientInfo;
import com.tuozuo.tavern.corp.assist.model.CorporationGroupInfo;

import java.util.List;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2021/1/2 <br>
 */
public interface CorporationGroupInfoDao {
    boolean insertGroup(CorporationGroupInfo corporationGroupInfo);

    boolean delGroup(CorporationGroupInfo corporationGroupInfo);

    boolean updateGroup(CorporationGroupInfo corporationGroupInfo);

    IPage<CorporationGroupClientInfo> selectGroups(Page<CorporationGroupClientInfo> page, String tagName, String groupName);

    CorporationGroupClientInfo selectGroupDetail(String groupId);


}
