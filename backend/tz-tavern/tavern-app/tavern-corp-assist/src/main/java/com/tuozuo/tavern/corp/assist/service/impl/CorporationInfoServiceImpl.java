package com.tuozuo.tavern.corp.assist.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tuozuo.tavern.corp.assist.model.CorporationInfo;
import com.tuozuo.tavern.corp.assist.service.CorporationInfoService;
import com.tuozuo.tavern.corp.assist.vo.CorporationInfoVO;
import org.springframework.stereotype.Service;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2021/1/5 <br>
 */
@Service
public class CorporationInfoServiceImpl implements CorporationInfoService {
    @Override
    public boolean addCorporation(CorporationInfoVO corporationInfoVO) {
        return false;
    }

    @Override
    public boolean delCorporation(String corpId) throws Exception {
        return false;
    }

    @Override
    public boolean modifyCorporation(CorporationInfoVO corporationInfoVO) {
        return false;
    }

    @Override
    public IPage<CorporationInfo> queryCorporations(String corpName, String clientName, int pageNo, int pageSize) {
        return null;
    }

    @Override
    public CorporationInfo queryCorporationDetail(String corpId) {
        return null;
    }
}
