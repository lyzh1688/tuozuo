package com.tuozuo.tavern.corp.assist.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Maps;
import com.tuozuo.tavern.corp.assist.convert.ModelMapConverterFactory;
import com.tuozuo.tavern.corp.assist.dao.CorporationClientInfoDao;
import com.tuozuo.tavern.corp.assist.dao.CorporationClientTagRelDao;
import com.tuozuo.tavern.corp.assist.dict.ValidType;
import com.tuozuo.tavern.corp.assist.model.CorporationClientInfo;
import com.tuozuo.tavern.corp.assist.model.CorporationClientTagInfo;
import com.tuozuo.tavern.corp.assist.model.CorporationClientTagRel;
import com.tuozuo.tavern.corp.assist.service.CorporationClientInfoService;
import com.tuozuo.tavern.corp.assist.utils.DateUtils;
import com.tuozuo.tavern.corp.assist.utils.UUIDUtil;
import com.tuozuo.tavern.corp.assist.vo.CorporationClientVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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
        corporationClientInfo.setClientId(UUIDUtil.randomUUID32());
        corporationClientInfo.setCreateTime(LocalDateTime.now());
        return this.corporationClientInfoDao.insertClient(corporationClientInfo);
    }

    @Override
    public boolean delClient(String clientId) {
        CorporationClientInfo corporationClientInfo = CorporationClientInfo.create(clientId, ValidType.INVALID.getType());
        return this.corporationClientInfoDao.delClient(corporationClientInfo);
    }

    @Override
    public boolean modifyClient(CorporationClientVO corporationClientVO) {
        CorporationClientInfo corporationClientInfo = this.converterFactory.dtoToCorporationClientInfo(corporationClientVO);
        return this.corporationClientInfoDao.updateClient(corporationClientInfo);
    }

    @Override
    public IPage<CorporationClientTagInfo> queryClients(String tagName, String clientName, int pageNo, int pageSize) {
        Page<CorporationClientTagInfo> page = new Page<CorporationClientTagInfo>(pageNo, pageSize);
        IPage<CorporationClientTagInfo> infoIPage = this.corporationClientInfoDao.selectClients(tagName, clientName, page);
        List<CorporationClientTagInfo> corporationClientTagInfos = this.createClientTagInfo(tagName, clientName, infoIPage.getRecords());
        infoIPage.setRecords(corporationClientTagInfos);
        int count = this.corporationClientInfoDao.selectClientsCnt(tagName, clientName);
        infoIPage.setTotal((long) count);
        return infoIPage;
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
            createTime = DateUtils.formatDateTime(LocalDateTime.now().plusMonths(1), DateUtils.DEFAULT_DATETIME_FORMATTER);
        }
        List<CorporationClientTagInfo> tagInfoList = this.corporationClientInfoDao.selectClientsFromApp(tagName, clientName, clientId, createTime);
        tagInfoList = this.createClientTagInfo(tagName, clientName, tagInfoList);
        return tagInfoList;
    }

    @Override
    public CorporationClientTagInfo queryClientDetail(String clientId, String type) {
        return this.corporationClientInfoDao.selectClientDetail(clientId, type);
    }


    private List<CorporationClientTagInfo> createClientTagInfo(String tagName, String clientName, List<CorporationClientTagInfo> tagInfoList) {
        if (Objects.nonNull(tagName)) {
            List<CorporationClientTagInfo> allClientInfos = this.corporationClientInfoDao.selectAllClients(tagName, clientName);
            Map<String, CorporationClientTagInfo> clientTagMap = allClientInfos.stream()
                    .collect(Collectors.toMap(CorporationClientTagInfo::getClientId, v -> v));
            tagInfoList = tagInfoList.stream()
                    .peek(client -> {
                        if (clientTagMap.containsKey(client.getClientId())) {
                            CorporationClientTagInfo tagInfo = clientTagMap.get(client.getClientId());
                            client.setTags(tagInfo.getTags());
                        }
                    }).collect(Collectors.toList());
        }
        return tagInfoList;
    }
}
