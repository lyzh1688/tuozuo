package com.tuozuo.tavern.shuiruyi.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tuozuo.tavern.shuiruyi.dao.BusinessDictMapper;
import com.tuozuo.tavern.shuiruyi.model.BusinessDict;
import com.tuozuo.tavern.shuiruyi.service.BusinessDictService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/7/18 <br>
 */
@Service
public class BusinessDictServiceImpl extends ServiceImpl<BusinessDictMapper, BusinessDict> implements BusinessDictService {

    @Override
    public List<BusinessDict> queryBuinessDicts(String group) {
        return this.list(Wrappers.<BusinessDict>query()
                .lambda()
                .eq(BusinessDict::getBusinessGroup, group)
                .orderByAsc(BusinessDict::getBusinessName));
    }
}
