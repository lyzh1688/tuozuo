package com.tuozuo.tavern.corp.assist.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tuozuo.tavern.corp.assist.model.CorporationInfo;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2021/1/5 <br>
 */
public interface CorporationInfoDao {

    boolean insertCorporation(CorporationInfo corporationInfo);

    boolean delCorporation(String corpId) throws Exception;

    boolean updateCorporation(CorporationInfo corporationInfo);

    IPage<CorporationInfo> selectCorporations(String corpName, String clientName, Page page);

    CorporationInfo selectCorporationDetail(String corpId);
}
