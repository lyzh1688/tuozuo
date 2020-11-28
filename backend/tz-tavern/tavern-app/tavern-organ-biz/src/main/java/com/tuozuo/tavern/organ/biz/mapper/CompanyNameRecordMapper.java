package com.tuozuo.tavern.organ.biz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tuozuo.tavern.organ.biz.model.CompanyNameRecord;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface CompanyNameRecordMapper extends BaseMapper<CompanyNameRecord> {

    int mergeBatch(List<CompanyNameRecord> record);
}