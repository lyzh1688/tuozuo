package com.tuozuo.tavern.organ.biz.service;

import com.tuozuo.tavern.organ.biz.model.RecordItem;
import com.tuozuo.tavern.organ.biz.model.RecordResult;
import com.tuozuo.tavern.organ.biz.model.UserCompanyName;

public interface CalculateRecordService {

    RecordResult handleRecord(RecordItem item, UserCompanyName companyName);
}
