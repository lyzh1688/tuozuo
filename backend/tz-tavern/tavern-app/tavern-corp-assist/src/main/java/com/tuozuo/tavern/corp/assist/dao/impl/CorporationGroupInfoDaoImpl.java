package com.tuozuo.tavern.corp.assist.dao.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tuozuo.tavern.corp.assist.dao.CorporationGroupInfoDao;
import com.tuozuo.tavern.corp.assist.mapper.CorporationGroupInfoMapper;
import com.tuozuo.tavern.corp.assist.model.CorporationClientInfo;
import com.tuozuo.tavern.corp.assist.model.CorporationGroupClientInfo;
import com.tuozuo.tavern.corp.assist.model.CorporationGroupInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2021/1/3 <br>
 */
@Repository
public class CorporationGroupInfoDaoImpl extends ServiceImpl<CorporationGroupInfoMapper, CorporationGroupInfo> implements CorporationGroupInfoDao {
    @Override
    public boolean insertGroup(CorporationGroupInfo corporationGroupInfo) {
        return this.save(corporationGroupInfo);
    }

    @Override
    public boolean delGroup(CorporationGroupInfo corporationGroupInfo) {
        return this.updateById(corporationGroupInfo);
    }

    @Override
    public boolean updateGroup(CorporationGroupInfo corporationGroupInfo) {
        return this.updateById(corporationGroupInfo);
    }

    @Override
    public IPage<CorporationGroupClientInfo> selectGroup(Page<CorporationGroupClientInfo> page, String tagName, String groupName) {
        return this.baseMapper.selectGroups(page, tagName, groupName);
    }

    @Override
    public CorporationGroupClientInfo selectGroupDetail(String groupId) {
        return this.baseMapper.selectGroupDetail(groupId);
    }

    @Override
    public List<CorporationGroupClientInfo> selectGroupsFromApp(String tagName, String groupName, String groupId, String createTime) {
        return this.baseMapper.selectGroupsFromApp(tagName, groupName, groupId, createTime);
    }

    @Override
    public CorporationGroupInfo selectGroupByChatId(String chatId) {
        return this.getOne(Wrappers.<CorporationGroupInfo>query()
                .lambda()
                .eq(CorporationGroupInfo::getChatId, chatId));
    }

    @Override
    public CorporationGroupClientInfo selectGroup(String chatId) {
        return this.baseMapper.selectGroupClients(chatId);
    }
}
