package com.tuozuo.tavern.corp.assist.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tuozuo.tavern.corp.assist.model.CorporationClientTagRel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface CorporationClientTagRelMapper extends BaseMapper<CorporationClientTagRel> {
    int insert(CorporationClientTagRel record);

    List<CorporationClientTagRel> selectAll();
}