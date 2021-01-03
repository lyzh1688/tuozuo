package com.tuozuo.tavern.corp.assist.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tuozuo.tavern.corp.assist.model.CorporationGroupClientInfo;
import com.tuozuo.tavern.corp.assist.model.CorporationGroupInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CorporationGroupInfoMapper extends BaseMapper<CorporationGroupInfo> {
    IPage<CorporationGroupClientInfo> selectGroups(Page<CorporationGroupClientInfo> page, String tagName, String groupName);

    CorporationGroupClientInfo selectGroupDetail(@Param("groupId") String groupId);
}