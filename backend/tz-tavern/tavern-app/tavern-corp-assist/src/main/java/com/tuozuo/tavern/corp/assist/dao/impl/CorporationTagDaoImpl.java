package com.tuozuo.tavern.corp.assist.dao.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tuozuo.tavern.corp.assist.dao.CorporationTagDao;
import com.tuozuo.tavern.corp.assist.mapper.CorporationTagInfoMapper;
import com.tuozuo.tavern.corp.assist.model.CorporationTagInfo;
import org.springframework.stereotype.Repository;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2021/1/2 <br>
 */
@Repository
public class CorporationTagDaoImpl extends ServiceImpl<CorporationTagInfoMapper,CorporationTagInfo> implements CorporationTagDao {
    @Override
    public boolean insertTag(CorporationTagInfo corporationTagInfo) {
        return this.save(corporationTagInfo);
    }

    @Override
    public boolean delTag(String tagId) {
        return this.removeById(tagId);
    }

    @Override
    public boolean updateTag(CorporationTagInfo corporationTagInfo) {
        return this.updateById(corporationTagInfo);
    }

    @Override
    public IPage<CorporationTagInfo> selectTags(Page<CorporationTagInfo> page) {
        return this.page(page);
    }
}
