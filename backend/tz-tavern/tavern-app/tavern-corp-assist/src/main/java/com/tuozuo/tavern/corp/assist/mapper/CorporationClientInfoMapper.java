package com.tuozuo.tavern.corp.assist.mapper;

import com.tuozuo.tavern.corp.assist.model.CorporationClientInfo;
import java.util.List;

public interface CorporationClientInfoMapper {
    int deleteByPrimaryKey(String clientId);

    int insert(CorporationClientInfo record);

    CorporationClientInfo selectByPrimaryKey(String clientId);

    List<CorporationClientInfo> selectAll();

    int updateByPrimaryKey(CorporationClientInfo record);
}