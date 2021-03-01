package com.tuozuo.tavern.corp.assist.service.impl;

import com.google.common.collect.Maps;
import com.tuozuo.tavern.corp.assist.convert.ModelMapConverterFactory;
import com.tuozuo.tavern.corp.assist.dao.BusinessDictDao;
import com.tuozuo.tavern.corp.assist.model.BusinessDict;
import com.tuozuo.tavern.corp.assist.service.BusinessDictService;
import com.tuozuo.tavern.corp.assist.utils.UUIDUtil;
import com.tuozuo.tavern.corp.assist.vo.CorporationBusinessDictVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    public List<BusinessDict> queryDicts(String businessName, String businessGroup) {
        return this.businessDictDao.selectDicts(businessName, businessGroup);
    }

    @Cacheable(value = "business_dict")
    @Override
    public Map<String, Map<String, String>> queryDicts() {
        List<BusinessDict> businessDicts = this.businessDictDao.selectDicts(null, null);
        Map<String, List<BusinessDict>> map = businessDicts.stream()
                .collect(Collectors.groupingBy(BusinessDict::getBusinessGroup));

        Map<String, Map<String, String>> bMap = Maps.newHashMap();
        map.entrySet().forEach(entry -> {
            List<BusinessDict> dicts = entry.getValue();
            Map<String, String> dictMap = dicts.stream()
                    .collect(Collectors.toMap(k -> k.getBusinessId(), v -> v.getBusinessName()));
            bMap.put(entry.getKey(), dictMap);
        });
        return bMap;
    }
}
