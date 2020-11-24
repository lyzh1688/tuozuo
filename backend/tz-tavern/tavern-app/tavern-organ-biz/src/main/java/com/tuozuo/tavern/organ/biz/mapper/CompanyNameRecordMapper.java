package com.tuozuo.tavern.organ.biz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tuozuo.tavern.organ.biz.model.CompanyNameRecord;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface CompanyNameRecordMapper extends BaseMapper<CompanyNameRecord> {
    int deleteByPrimaryKey(@Param("pinyin") String pinyin, @Param("fullName") String fullName);

    int insert(CompanyNameRecord record);

    CompanyNameRecord selectByPrimaryKey(@Param("pinyin") String pinyin, @Param("fullName") String fullName);

    List<CompanyNameRecord> selectAll();

    int updateByPrimaryKey(CompanyNameRecord record);
}