package com.tuozuo.tavern.corp.assist.dao.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tuozuo.tavern.corp.assist.dao.CorporationClientTagRelDao;
import com.tuozuo.tavern.corp.assist.mapper.CorporationClientTagRelMapper;
import com.tuozuo.tavern.corp.assist.model.CorporationClientTagRel;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2021/1/3 <br>
 */
@Repository
public class CorporationClientTagRelDaoImpl extends ServiceImpl<CorporationClientTagRelMapper,CorporationClientTagRel> implements CorporationClientTagRelDao {

    @Override
    public boolean insertBatch(List<CorporationClientTagRel> rels) {
        return this.saveBatch(rels);
    }

    @Override
    public boolean delByClientId(String clientId) {
        return this.remove(Wrappers.<CorporationClientTagRel>query()
                .lambda()
                .eq(CorporationClientTagRel::getClientId, clientId));
    }
}
