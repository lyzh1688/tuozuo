package com.tuozuo.tavern.corp.assist.dao;

import com.tuozuo.tavern.corp.assist.model.CorporationClientGroupRel;

import java.util.List;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2021/1/3 <br>
 */
public interface CorporationClientGroupRelDao {

    boolean delByGroupId(String groupId);

    boolean insertBatch(List<CorporationClientGroupRel> rels);

    boolean isValidGroup(String groupId);
}
