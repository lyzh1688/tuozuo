package com.tuozuo.tavern.shuiruyi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tuozuo.tavern.shuiruyi.model.CustomDetailInfo;
import com.tuozuo.tavern.shuiruyi.model.CustomInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CustomInfoMapper extends BaseMapper<CustomInfo> {

    CustomDetailInfo selectDetailInfo(@Param("customId") String customId);

    IPage<CustomInfo> selectCustomInfoList(Page page, @Param("customName") String customName, @Param("hasPaid") String hasPaid);


}