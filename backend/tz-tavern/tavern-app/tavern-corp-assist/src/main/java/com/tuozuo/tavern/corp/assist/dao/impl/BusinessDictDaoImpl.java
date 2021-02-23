package com.tuozuo.tavern.corp.assist.dao.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tuozuo.tavern.corp.assist.dao.BusinessDictDao;
import com.tuozuo.tavern.corp.assist.mapper.BusinessDictMapper;
import com.tuozuo.tavern.corp.assist.model.BusinessDict;
import org.springframework.stereotype.Repository;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2021/2/20 <br>
 */
@Repository
public class BusinessDictDaoImpl extends ServiceImpl<BusinessDictMapper, BusinessDict> implements BusinessDictDao {
    @Override
    public boolean insertDict(BusinessDict businessDict) {
        return this.save(businessDict);
    }

    @Override
    public boolean delDict(String dictId) {
        return this.removeById(dictId);
    }

    @Override
    public boolean updateDict(BusinessDict businessDict) {
        return this.update(businessDict, Wrappers.<BusinessDict>query()
                .lambda()
                .eq(StringUtils.isNotEmpty(businessDict.getBusinessGroup()),BusinessDict::getBusinessGroup, businessDict.getBusinessGroup())
                .eq(StringUtils.isNotEmpty(businessDict.getBusinessId()),BusinessDict::getBusinessId, businessDict.getBusinessId()));
    }

    @Override
    public IPage<BusinessDict> selectDicts(Page<BusinessDict> page, String businessName, String businessGroup) {
        return this.page(page, Wrappers.<BusinessDict>query()
                .lambda()
                .like(StringUtils.isNotEmpty(businessName), BusinessDict::getBusinessName, businessName)
                .like(StringUtils.isNotEmpty(businessGroup), BusinessDict::getBusinessGroup, businessGroup)
                .orderByAsc(BusinessDict::getBusinessGroup));
    }
}
