package com.tuozuo.tavern.corp.assist.mapper;

import com.tuozuo.tavern.corp.assist.model.CorporationGroupInfo;
import java.util.List;

public interface CorporationGroupInfoMapper {
    int deleteByPrimaryKey(String groupId);

    int insert(CorporationGroupInfo record);

    CorporationGroupInfo selectByPrimaryKey(String groupId);

    List<CorporationGroupInfo> selectAll();

    int updateByPrimaryKey(CorporationGroupInfo record);
}