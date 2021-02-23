package com.tuozuo.tavern.corp.assist.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tuozuo.tavern.corp.assist.convert.ModelMapConverterFactory;
import com.tuozuo.tavern.corp.assist.dao.BusinessDictDao;
import com.tuozuo.tavern.corp.assist.model.BusinessDict;
import com.tuozuo.tavern.corp.assist.service.BusinessDictService;
import com.tuozuo.tavern.corp.assist.utils.UUIDUtil;
import com.tuozuo.tavern.corp.assist.vo.CorporationBusinessDictVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2021/2/22 <br>
 */
@Service
public class BusinessDictServiceImpl implements BusinessDictService {
    @Autowired
    private BusinessDictDao businessDictDao;
    @Autowired
    private ModelMapConverterFactory factory;

    @Override
    public boolean saveDict(CorporationBusinessDictVO vo) {
        BusinessDict businessDict = this.factory.voToBusinessDict(vo);
        businessDict.setBusinessId(UUIDUtil.randomUUID32());
        return this.businessDictDao.insertDict(businessDict);
    }

    @Override
    public boolean delDict(String dictId) {
        return this.businessDictDao.delDict(dictId);
    }

    @Override
    public boolean modifyDict(CorporationBusinessDictVO vo) {
        BusinessDict businessDict = this.factory.voToBusinessDict(vo);
        return this.businessDictDao.updateDict(businessDict);
    }

    @Override
    public IPage<BusinessDict> queryDicts(int pageNo, int pageSize, String businessName,String businessGroup) {
        Page<BusinessDict> page = new Page<>(pageNo, pageSize);
        return this.businessDictDao.selectDicts(page,businessName,businessGroup);
    }
}
