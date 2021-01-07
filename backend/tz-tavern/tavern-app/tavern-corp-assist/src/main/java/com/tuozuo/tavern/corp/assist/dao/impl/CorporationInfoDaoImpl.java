package com.tuozuo.tavern.corp.assist.dao.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tuozuo.tavern.corp.assist.dao.CorporationInfoDao;
import com.tuozuo.tavern.corp.assist.mapper.CorporationInfoMapper;
import com.tuozuo.tavern.corp.assist.model.CorporationInfo;
import org.springframework.stereotype.Repository;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2021/1/5 <br>
 */
@Repository
public class CorporationInfoDaoImpl extends ServiceImpl<CorporationInfoMapper,CorporationInfo> implements CorporationInfoDao {
    @Override
    public boolean insertCorporation(CorporationInfo corporationInfo) {
        return this.save(corporationInfo);
    }

    @Override
    public boolean delCorporation(String corpId) throws Exception {
        return this.removeById(corpId);
    }

    @Override
    public boolean updateCorporation(CorporationInfo corporationInfo) {
        return this.updateById(corporationInfo);
    }

    @Override
    public IPage<CorporationInfo> selectCorporations(String corpName, String clientName, Page page) {
        return null;
    }

    @Override
    public CorporationInfo selectCorporationDetail(String corpId) {
        return this.getById(corpId);
    }
}
