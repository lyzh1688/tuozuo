package com.tuozuo.tavern.corp.assist.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tuozuo.tavern.corp.assist.model.CorporationClientCorpInfo;
import com.tuozuo.tavern.corp.assist.model.CorporationClientInfo;
import com.tuozuo.tavern.corp.assist.model.CorporationClientTagInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CorporationClientInfoMapper extends BaseMapper<CorporationClientInfo> {

    IPage<CorporationClientTagInfo> selectClients(Page page,
                                                  @Param("tagName") String tagName,
                                                  @Param("clientName") String clientName,
                                                  @Param("corpName") String corpName
                                                  );

    int selectClientsCnt(@Param("tagName") String tagName,
                         @Param("clientName") String clientName,
                         @Param("corpName") String corpName);


    List<CorporationClientTagInfo> selectClientsFromApp(@Param("tagName") String tagName,
                                                        @Param("clientName") String clientName,
                                                        @Param("clientId") String clientId,
                                                        @Param("createTime") String createTime,
                                                        @Param("corpName") String corpName);

    CorporationClientTagInfo selectClientDetail(@Param("clientId") String clientId, @Param("type") String type);


    List<CorporationClientTagInfo> selectAllClients(@Param("tagName") String tagName,
                                                    @Param("clientName") String clientName);

    List<CorporationClientCorpInfo> selectClientCorpInfo(@Param("list") List<String> clientIds);


    List<CorporationClientInfo> selectClientsByUserIds(@Param("list") List<String> userIds);
}