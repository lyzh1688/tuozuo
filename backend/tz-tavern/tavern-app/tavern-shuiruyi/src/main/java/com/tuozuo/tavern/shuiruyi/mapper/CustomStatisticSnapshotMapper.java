package com.tuozuo.tavern.shuiruyi.mapper;

import com.tuozuo.tavern.shuiruyi.model.CustomStatisticSnapshot;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface CustomStatisticSnapshotMapper {
    int deleteByPrimaryKey(String customId);

    int insert(CustomStatisticSnapshot record);

    CustomStatisticSnapshot selectByPrimaryKey(String customId);

    List<CustomStatisticSnapshot> selectAll();

    int updateByPrimaryKey(CustomStatisticSnapshot record);
}