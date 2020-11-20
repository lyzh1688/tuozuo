package com.tuozuo.tavern.organ.biz.service.impl;

import com.tuozuo.tavern.organ.biz.model.CompanyName;
import com.tuozuo.tavern.organ.biz.service.CompanyNameService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/11/20 <br>
 */
@Service
public class CompanyNameServiceImpl implements CompanyNameService {
    @Override
    public List<CompanyName> queryCompanyName(String area, String industry, String source, String preferWord, Boolean isTwoWords) {
        return null;
    }
}
