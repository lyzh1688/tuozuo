package com.tuozuo.tavern.shuiruyi.dao;

import com.tuozuo.tavern.shuiruyi.model.CustomStatisticSnapshot;
import java.util.List;

public interface CustomStatisticSnapshotMapper {
    int deleteByPrimaryKey(String customId);

    int insert(CustomStatisticSnapshot record);

    CustomStatisticSnapshot selectByPrimaryKey(String customId);

    List<CustomStatisticSnapshot> selectAll();

    int updateByPrimaryKey(CustomStatisticSnapshot record);
}