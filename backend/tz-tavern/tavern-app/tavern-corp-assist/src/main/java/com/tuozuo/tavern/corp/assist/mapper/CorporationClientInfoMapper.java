package com.tuozuo.tavern.corp.assist.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tuozuo.tavern.corp.assist.model.CorporationClientInfo;
import com.tuozuo.tavern.corp.assist.model.CorporationClientTagInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CorporationClientInfoMapper extends BaseMapper<CorporationClientInfo> {

    IPage<CorporationClientTagInfo> selectClients(Page page,
                                                  @Param("tagName") String tagName,
                                                  @Param("clientName") String clientName);
}