package com.tuozuo.tavern.corp.assist.dao.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tuozuo.tavern.corp.assist.dao.CorporationClientInfoDao;
import com.tuozuo.tavern.corp.assist.mapper.CorporationClientInfoMapper;
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
    public IPage<CorporationClientTagInfo> queryClients(String tagName, String clientName, Page<CorporationClientTagInfo> page) {
        return this.baseMapper.selectClients(page,tagName,clientName);
    }

    @Override
    public List<CorporationClientTagInfo> selectClientsFromApp(String tagName, String clientName, String clientId, String createTime) {
        return this.baseMapper.selectClientsFromApp(tagName, clientName, clientId, createTime);
    }
}
