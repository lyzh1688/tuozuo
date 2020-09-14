package com.tuozuo.tavern.xinruyi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tuozuo.tavern.xinruyi.model.EventFinishList;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface EventFinishListMapper extends BaseMapper<EventFinishList> {

    IPage<EventFinishList> selectEventList(Page page,
                                           @Param("companyId") String companyId,
                                           @Param("projectId") String projectId,
                                           @Param("eventId") String eventId,
                                           @Param("role") String role );
}