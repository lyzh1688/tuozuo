package com.tuozuo.tavern.corp.assist.dao.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tuozuo.tavern.corp.assist.dao.CorporationClientInfoDao;
import com.tuozuo.tavern.corp.assist.mapper.CorporationClientInfoMapper;
import com.tuozuo.tavern.corp.assist.model.CorporationClientCorpInfo;
import com.tuozuo.tavern.corp.assist.model.CorporationClientGroupRel;
import com.tuozuo.tavern.corp.assist.model.CorporationClientInfo;
import com.tuozuo.tavern.corp.assist.model.CorporationClientTagInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2021/1/2 <br>
 */
@Repository
public class CorporationClientInfoDaoImpl extends ServiceImpl<CorporationClientInfoMapper, CorporationClientInfo> implements CorporationClientInfoDao {
    @Override
    public boolean insertClient(CorporationClientInfo corporationClientInfo) {
        return this.save(corporationClientInfo);
    }

    @Override
    public boolean delClient(CorporationClientInfo corporationClientInfo) {
        return this.updateById(corporationClientInfo);
    }

    @Override
    public boolean updateClient(CorporationClientInfo corporationClientInfo) {
        return this.updateById(corporationClientInfo);
    }

    @Override
    public IPage<CorporationClientTagInfo> selectClients(String tagName, String clientName, Page<CorporationClientTagInfo> page,String corpName) {
        return this.baseMapper.selectClients(page, tagName, clientName,corpName);
    }

    @Override
    public int selectClientsCnt(String tagName, String clientName,String corpName) {
        return this.baseMapper.selectClientsCnt(tagName, clientName,corpName);
    }

    @Override
    public List<CorporationClientTagInfo> selectClientsFromApp(String tagName, String clientName, String clientId, String createTime,String corpName) {
        return this.baseMapper.selectClientsFromApp(tagName, clientName, clientId, createTime,corpName);
    }

    @Override
    public CorporationClientTagInfo selectClientDetail(String clientId, String type) {
        return this.baseMapper.selectClientDetail(clientId, type);
    }

    @Override
    public CorporationClientInfo selectClient(String clientId, String type) {
        if (type.equals("1")) {
            return this.getOne(Wrappers.<CorporationClientInfo>query()
                    .lambda()
                    .eq(CorporationClientInfo::getClientId, clientId));
        } else {
            return this.getOne(Wrappers.<CorporationClientInfo>query()
                    .lambda()
                    .eq(CorporationClientInfo::getUserId, clientId));
        }
    }

    @Override
    public List<CorporationClientInfo> selectClientsByUserIds(List<String> userIds) {
        return this.baseMapper.selectClientsByUserIds(userIds);
    }

    @Override
    public List<CorporationClientTagInfo> selectAllClients(String tagName, String clientName) {
        return this.baseMapper.selectAllClients(tagName, clientName);
    }

    @Override
    public List<CorporationClientCorpInfo> selectClientCorpInfo(List<String> clientIds) {
        return this.baseMapper.selectClientCorpInfo(clientIds);
    }
}
