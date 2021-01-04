package com.tuozuo.tavern.corp.assist.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tuozuo.tavern.corp.assist.model.CorporationTagInfo;

import java.util.List;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2021/1/2 <br>
 */
public interface CorporationTagService {

    boolean addTag(String tagName);

    boolean delTag(String tagId) throws Exception;

    boolean modifyTag(String tagId, String tagName);

    IPage<CorporationTagInfo> queryTags(String tagName, int pageNo, int pageSize);

    List<CorporationTagInfo> queryTagsByName(String tagName, int queryCnt);

    List<CorporationTagInfo> queryTagsFromApp(String tagName, String tagId);

}
