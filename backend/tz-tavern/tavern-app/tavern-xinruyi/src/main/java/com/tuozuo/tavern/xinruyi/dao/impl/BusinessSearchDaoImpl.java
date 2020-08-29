package com.tuozuo.tavern.xinruyi.dao.impl;

import com.tuozuo.tavern.xinruyi.dao.BusinessSearchDao;
import com.tuozuo.tavern.xinruyi.mapper.BankInfoMapper;
import com.tuozuo.tavern.xinruyi.model.BankInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/8/29 <br>
 */
@Repository
public class BusinessSearchDaoImpl implements BusinessSearchDao {

    @Autowired
    private BankInfoMapper bankInfoMapper;

    @Override
    public List<BankInfo> selectBankInfo(String bankCode) {
        return this.bankInfoMapper.selectList(bankCode);
    }
}
