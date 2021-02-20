package com.tuozuo.tavern.corp.assist.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tuozuo.tavern.corp.assist.model.CorporationInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CorporationInfoMapper extends BaseMapper<CorporationInfo> {

    IPage<CorporationInfo> selectByPage(Page<CorporationInfo> page, @Param("corpName") String corpName, @Param("clientName") String clientName);

    List<CorporationInfo> selectFromApp(@Param("corpName") String corpName, @Param("clientName") String clientName,
                                        @Param("corpId") String corpId, @Param("createTime") String createTime);

}