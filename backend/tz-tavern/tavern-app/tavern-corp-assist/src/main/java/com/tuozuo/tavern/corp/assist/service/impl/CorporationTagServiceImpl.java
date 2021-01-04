package com.tuozuo.tavern.corp.assist.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tuozuo.tavern.corp.assist.dao.CorporationTagDao;
import com.tuozuo.tavern.corp.assist.model.CorporationTagInfo;
import com.tuozuo.tavern.corp.assist.service.CorporationTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2021/1/2 <br>
 */
@Service
public class CorporationTagServiceImpl implements CorporationTagService {

    @Autowired
    private CorporationTagDao corporationTagDao;

    @Override
    public boolean addTag(String tagName) {
        CorporationTagInfo corporationTagInfo = CorporationTagInfo.create(tagName);
        return this.corporationTagDao.insertTag(corporationTagInfo);
    }

    @Override
    public boolean delTag(String tagId) {
        return this.corporationTagDao.delTag(tagId);
    }

    @Override
    public boolean modifyTag(String tagId, String tagName) {
        CorporationTagInfo corporationTagInfo = new CorporationTagInfo(tagId, tagName);
        return this.corporationTagDao.updateTag(corporationTagInfo);
    }

    @Override
    public IPage<CorporationTagInfo> queryTags(String tagName,int pageNo, int pageSize) {
        Page<CorporationTagInfo> page = new Page<>(pageNo, pageSize);
        return this.corporationTagDao.selectTags(page,tagName);
    }

    @Override
    public List<CorporationTagInfo> queryTagsByName(String tagName, int queryCnt) {
        return this.corporationTagDao.selectTagsByName(tagName, queryCnt);
    }
}
