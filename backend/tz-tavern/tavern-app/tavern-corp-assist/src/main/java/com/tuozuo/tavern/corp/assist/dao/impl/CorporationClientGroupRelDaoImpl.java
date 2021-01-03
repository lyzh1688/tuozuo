package com.tuozuo.tavern.corp.assist.dao.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tuozuo.tavern.corp.assist.dao.CorporationClientGroupRelDao;
import com.tuozuo.tavern.corp.assist.mapper.CorporationClientGroupRelMapper;
import com.tuozuo.tavern.corp.assist.model.CorporationClientGroupRel;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2021/1/3 <br>
 */
@Repository
public class CorporationClientGroupRelDaoImpl extends ServiceImpl<CorporationClientGroupRelMapper, CorporationClientGroupRel> implements CorporationClientGroupRelDao {
    @Override
    public boolean delByGroupId(String groupId) {
        return this.remove(Wrappers.<CorporationClientGroupRel>query()
                .lambda()
                .eq(CorporationClientGroupRel::getGroupId, groupId));
    }

    @Override
    public boolean insertBatch(List<CorporationClientGroupRel> rels) {
        return this.saveBatch(rels);
    }
}
