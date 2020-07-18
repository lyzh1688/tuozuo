package com.tuozuo.tavern.shuiruyi.dao.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.tuozuo.tavern.shuiruyi.dao.BusinessDictDao;
import com.tuozuo.tavern.shuiruyi.mapper.BusinessDictMapper;
import com.tuozuo.tavern.shuiruyi.model.BusinessDict;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/7/18 <br>
 */
@Repository
public class BusinessDictDaoImpl implements BusinessDictDao {
    @Autowired
    private BusinessDictMapper businessDictMapper;

    @Override
    public List<BusinessDict> selectBuinessDicts(String group) {
        return this.businessDictMapper.selectList(Wrappers.<BusinessDict>query()
                .lambda()
                .eq(BusinessDict::getBusinessGroup, group)
                .orderByAsc(BusinessDict::getBusinessName));
    }
}
