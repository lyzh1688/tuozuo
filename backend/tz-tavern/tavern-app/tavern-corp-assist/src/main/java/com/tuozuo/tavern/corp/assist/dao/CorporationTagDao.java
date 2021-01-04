package com.tuozuo.tavern.corp.assist.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tuozuo.tavern.corp.assist.model.CorporationTagInfo;

import java.util.List;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2021/1/2 <br>
 */
public interface CorporationTagDao {

    boolean insertTag(CorporationTagInfo corporationTagInfo);

    boolean delTag(String tagId);

    boolean updateTag(CorporationTagInfo corporationTagInfo);

    IPage<CorporationTagInfo> selectTags(Page<CorporationTagInfo> page,String tagName);

    List<CorporationTagInfo> selectTagsByName(String tagName, int queryCnt);

    List<CorporationTagInfo> selectTagsFromApp(String tagName, String tagId);

}
