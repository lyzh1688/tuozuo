package com.tuozuo.tavern.corp.assist.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tuozuo.tavern.corp.assist.convert.ModelMapConverterFactory;
import com.tuozuo.tavern.corp.assist.dao.CorporationInfoDao;
import com.tuozuo.tavern.corp.assist.dict.ValidType;
import com.tuozuo.tavern.corp.assist.model.CorporationGroupClientInfo;
import com.tuozuo.tavern.corp.assist.model.CorporationInfo;
import com.tuozuo.tavern.corp.assist.service.CorporationInfoService;
import com.tuozuo.tavern.corp.assist.utils.DateUtils;
import com.tuozuo.tavern.corp.assist.vo.CorporationInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2021/1/5 <br>
 */
@Service
public class CorporationInfoServiceImpl implements CorporationInfoService {

    @Autowired
    private CorporationInfoDao corporationInfoDao;
    @Autowired
    private ModelMapConverterFactory converterFactory;


    @Override
    public boolean addCorporation(CorporationInfoVO corporationInfoVO) {
        CorporationInfo corporationInfo = this.converterFactory.voToCorporationInfo(corporationInfoVO);
        corporationInfo.setCreateTime(LocalDateTime.now());
        return this.corporationInfoDao.insertCorporation(corporationInfo);
    }

    @Override
    public boolean delCorporation(String corpId) {
        CorporationInfo corporationInfo = CorporationInfo.create(corpId, ValidType.INVALID.getType());
        return this.corporationInfoDao.delCorporation(corporationInfo);
    }

    @Override
    public boolean modifyCorporation(CorporationInfoVO corporationInfoVO) {
        CorporationInfo corporationInfo = this.converterFactory.voToCorporationInfo(corporationInfoVO);
        return this.corporationInfoDao.updateCorporation(corporationInfo);
    }

    @Override
    public IPage<CorporationInfo> queryCorporations(String corpName, String clientName, int pageNo, int pageSize) {
        Page<CorporationInfo> page = new Page<>(pageNo, pageSize);
        return this.corporationInfoDao.selectCorporations(corpName, clientName, page);
    }

    @Override
    public List<CorporationInfo> queryCorporationsFromApp(String corpName, String clientName, String corpId, String createTime) {
        if (StringUtils.isEmpty(createTime)) {
            createTime = DateUtils.formatDateTime(LocalDateTime.now().plusMonths(1), DateUtils.DEFAULT_DATETIME_FORMATTER);
        }
        return this.corporationInfoDao.selectCorporationsFromApp(corpName, clientName, corpId, createTime);
    }

    @Override
    public CorporationInfo queryCorporationDetail(String corpId) {
        return null;
    }
}
