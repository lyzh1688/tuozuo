package com.tuozuo.tavern.xinruyi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tuozuo.tavern.xinruyi.model.BankInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface BankInfoMapper extends BaseMapper<BankInfo> {

    List<BankInfo> selectList(@Param("bankCode") String bankCode,
                              @Param("bankName") String bankName,
                              @Param("queryCnt") int queryCnt);

}