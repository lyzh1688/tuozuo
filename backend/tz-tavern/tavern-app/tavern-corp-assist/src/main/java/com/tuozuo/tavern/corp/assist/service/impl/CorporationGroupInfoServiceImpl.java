package com.tuozuo.tavern.corp.assist.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tuozuo.tavern.corp.assist.dao.CorporationGroupInfoDao;
import com.tuozuo.tavern.corp.assist.dict.ValidType;
import com.tuozuo.tavern.corp.assist.model.CorporationGroupClientInfo;
import com.tuozuo.tavern.corp.assist.model.CorporationGroupInfo;
import com.tuozuo.tavern.corp.assist.service.CorporationGroupInfoService;
import com.tuozuo.tavern.corp.assist.vo.CorporationGroupInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2021/1/3 <br>
 */
@Service
public class CorporationGroupInfoServiceImpl implements CorporationGroupInfoService {

    @Autowired
    private CorporationGroupInfoDao corporationGroupInfoDao;


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
}
