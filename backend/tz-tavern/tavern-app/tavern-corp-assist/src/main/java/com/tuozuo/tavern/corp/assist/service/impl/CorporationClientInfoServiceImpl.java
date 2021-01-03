package com.tuozuo.tavern.corp.assist.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tuozuo.tavern.corp.assist.convert.ModelMapConverterFactory;
import com.tuozuo.tavern.corp.assist.dao.CorporationClientInfoDao;
import com.tuozuo.tavern.corp.assist.dao.CorporationClientTagRelDao;
import com.tuozuo.tavern.corp.assist.model.CorporationClientInfo;
import com.tuozuo.tavern.corp.assist.model.CorporationClientTagInfo;
import com.tuozuo.tavern.corp.assist.model.CorporationClientTagRel;
import com.tuozuo.tavern.corp.assist.service.CorporationClientInfoService;
import com.tuozuo.tavern.corp.assist.utils.DateUtils;
import com.tuozuo.tavern.corp.assist.vo.CorporationClientVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2021/1/2 <br>
 */
@Service
public class CorporationClientInfoServiceImpl implements CorporationClientInfoService {

    @Autowired
    private CorporationClientInfoDao corporationClientInfoDao;
    @Autowired
    private CorporationClientTagRelDao corporationClientTagRelDao;

    @Autowired
    private ModelMapConverterFactory converterFactory;

    @Override
    public boolean addClient(CorporationClientVO corporationClientVO) {
        CorporationClientInfo corporationClientInfo = this.converterFactory.dtoToCorporationClientInfo(corporationClientVO);
        return this.corporationClientInfoDao.insertClient(corporationClientInfo);
    }

    @Override
    public boolean delClient(String clientId) {
        return this.corporationClientInfoDao.delClient(clientId);
    }

    @Override
    public boolean modifyClient(CorporationClientVO corporationClientVO) {
        CorporationClientInfo corporationClientInfo = this.converterFactory.dtoToCorporationClientInfo(corporationClientVO);
        return this.corporationClientInfoDao.updateClient(corporationClientInfo);
    }

    @Override
    public IPage<CorporationClientTagInfo> queryClients(String tagName, String clientName, int pageNo, int pageSize) {
        Page<CorporationClientTagInfo> page = new Page<CorporationClientTagInfo>(pageNo, pageSize);
        return this.corporationClientInfoDao.queryClients(tagName, clientName, page);
    }

    @Transactional
    @Override
    public boolean bindClientTag(String clientId, List<String> tags) {
        List<CorporationClientTagRel> rels = tags.stream()
                .map(t -> CorporationClientTagRel.create(clientId, t))
                .collect(Collectors.toList());
        this.corporationClientTagRelDao.delByClientId(clientId);
        return this.corporationClientTagRelDao.insertBatch(rels);
    }

    @Override
    public List<CorporationClientTagInfo> queryClientsFromApp(String tagName, String clientName, String clientId, String createTime) {
        if (StringUtils.isEmpty(createTime)) {
            createTime = DateUtils.formatDate(LocalDate.now().plusMonths(1), DateUtils.DEFAULT_DATETIME_FORMATTER);
        }
        return this.corporationClientInfoDao.selectClientsFromApp(tagName, clientName, clientId, createTime);
    }
}
