package com.tuozuo.tavern.corp.assist.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tuozuo.tavern.corp.assist.convert.ModelMapConverterFactory;
import com.tuozuo.tavern.corp.assist.dao.CorporationClientInfoDao;
import com.tuozuo.tavern.corp.assist.model.CorporationClientInfo;
import com.tuozuo.tavern.corp.assist.model.CorporationClientTagInfo;
import com.tuozuo.tavern.corp.assist.service.CorporationClientInfoService;
import com.tuozuo.tavern.corp.assist.vo.CorporationClientVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2021/1/2 <br>
 */
@Service
public class CorporationClientInfoServiceImpl implements CorporationClientInfoService {

    @Autowired
    private CorporationClientInfoDao corporationClientInfoDao;

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
        Page<CorporationClientTagInfo> page = new Page<CorporationClientTagInfo>(pageNo,pageSize);
        return this.corporationClientInfoDao.queryClients(tagName,clientName,page);
    }
}
