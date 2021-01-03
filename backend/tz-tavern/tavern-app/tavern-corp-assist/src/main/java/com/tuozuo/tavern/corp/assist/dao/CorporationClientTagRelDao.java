package com.tuozuo.tavern.corp.assist.dao;

import com.tuozuo.tavern.corp.assist.model.CorporationClientTagRel;

import java.util.List;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2021/1/3 <br>
 */
public interface CorporationClientTagRelDao {

    boolean insertBatch(List<CorporationClientTagRel> rels);

    boolean delByClientId(String clientId);
}
