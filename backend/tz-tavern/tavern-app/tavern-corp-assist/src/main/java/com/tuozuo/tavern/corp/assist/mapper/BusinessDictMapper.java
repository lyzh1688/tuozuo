package com.tuozuo.tavern.corp.assist.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tuozuo.tavern.corp.assist.model.BusinessDict;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface BusinessDictMapper extends BaseMapper<BusinessDict> {
}