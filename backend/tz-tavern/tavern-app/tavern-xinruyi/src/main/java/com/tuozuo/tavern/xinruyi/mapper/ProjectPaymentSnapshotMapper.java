package com.tuozuo.tavern.xinruyi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tuozuo.tavern.xinruyi.model.ProjectPaymentSnapshot;
import org.apache.ibatis.annotations.Mapper;
@Mapper
public interface ProjectPaymentSnapshotMapper extends BaseMapper<ProjectPaymentSnapshot> {

    void insertOrUpdate(ProjectPaymentSnapshot record);
}