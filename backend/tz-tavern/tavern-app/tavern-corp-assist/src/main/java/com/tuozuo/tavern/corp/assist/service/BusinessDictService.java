package com.tuozuo.tavern.corp.assist.service;

import com.tuozuo.tavern.corp.assist.model.BusinessDict;
import com.tuozuo.tavern.corp.assist.vo.CorporationBusinessDictVO;

import java.util.List;
import java.util.Map;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2021/2/20 <br>
 */
public interface BusinessDictService {

    boolean saveDict(CorporationBusinessDictVO vo);

    boolean delDict(String dictId);

    boolean modifyDict(CorporationBusinessDictVO vo);

    List<BusinessDict> queryDicts(String businessName, String businessGroup);

    Map<String,Map<String,String>> queryDicts();

}
