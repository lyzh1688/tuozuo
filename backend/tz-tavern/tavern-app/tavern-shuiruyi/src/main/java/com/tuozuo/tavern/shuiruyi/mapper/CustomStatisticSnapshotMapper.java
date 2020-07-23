package com.tuozuo.tavern.shuiruyi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tuozuo.tavern.shuiruyi.model.CustomStatisticSnapshot;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CustomStatisticSnapshotMapper extends BaseMapper<CustomStatisticSnapshot> {

    void countStatistic();

}