package com.tuozuo.tavern.corp.assist.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tuozuo.tavern.corp.assist.dao.CorporationClientGroupRelDao;
import com.tuozuo.tavern.corp.assist.dao.CorporationGroupInfoDao;
import com.tuozuo.tavern.corp.assist.dict.ValidType;
import com.tuozuo.tavern.corp.assist.model.CorporationClientGroupRel;
import com.tuozuo.tavern.corp.assist.model.CorporationGroupClientInfo;
import com.tuozuo.tavern.corp.assist.model.CorporationGroupInfo;
import com.tuozuo.tavern.corp.assist.service.CorporationGroupInfoService;
import com.tuozuo.tavern.corp.assist.utils.DateUtils;
import com.tuozuo.tavern.corp.assist.vo.CorporationGroupInfoVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2021/1/3 <br>
 */
@Service
public class CorporationGroupInfoServiceImpl implements CorporationGroupInfoService {

    @Autowired
    private CorporationGroupInfoDao corporationGroupInfoDao;
    @Autowired
    private CorporationClientGroupRelDao corporationClientGroupRelDao;


    @Override
    public boolean addGroup(CorporationGroupInfoVO groupInfoVO) {
        CorporationGroupInfo corporationGroupInfo = CorporationGroupInfo.create(groupInfoVO.getChatId(), groupInfoVO.getGroupName(), groupInfoVO.getGroupNotice());
        return this.corporationGroupInfoDao.insertGroup(corporationGroupInfo);
    }

    @Override
    public boolean delGroup(String groupId) {
        CorporationGroupInfo corporationGroupInfo = CorporationGroupInfo.create(groupId, ValidType.INVALID.getType());
        return this.corporationGroupInfoDao.delGroup(corporationGroupInfo);
    }

    @Override
    public boolean modifyGroup(CorporationGroupInfoVO groupInfoVO) {
        CorporationGroupInfo corporationGroupInfo = CorporationGroupInfo.create(groupInfoVO.getGroupId(), groupInfoVO.getChatId(), groupInfoVO.getGroupName(), groupInfoVO.getGroupNotice());
        return this.corporationGroupInfoDao.updateGroup(corporationGroupInfo);
    }

    @Override
    public IPage<CorporationGroupClientInfo> queryGroups(String tagName, String groupName, int pageNo, int pageSize) {
        Page<CorporationGroupClientInfo> page = new Page<>(pageNo, pageSize);
        return this.corporationGroupInfoDao.selectGroups(page, tagName, groupName);
    }

    @Override
    public CorporationGroupClientInfo queryGroupDetail(String groupId) {
        return this.corporationGroupInfoDao.selectGroupDetail(groupId);
    }

    @Transactional
    @Override
    public boolean bindGroupClientRel(String groupId, List<String> clientId) {
        List<CorporationClientGroupRel> relList = clientId.stream()
                .map(c -> CorporationClientGroupRel.create(c, groupId))
                .collect(Collectors.toList());
        this.corporationClientGroupRelDao.delByGroupId(groupId);
        return this.corporationClientGroupRelDao.insertBatch(relList);
    }

    @Override
    public List<CorporationGroupClientInfo> queryGroupsFromApp(String tagName, String groupName, String groupId, String createTime) {
        if(StringUtils.isEmpty(createTime)){
            createTime = DateUtils.formatDate(LocalDate.now().plusMonths(1), DateUtils.DEFAULT_DATETIME_FORMATTER);
        }
        return this.corporationGroupInfoDao.selectGroupsFromApp(tagName, groupName, groupId, createTime);
    }
}