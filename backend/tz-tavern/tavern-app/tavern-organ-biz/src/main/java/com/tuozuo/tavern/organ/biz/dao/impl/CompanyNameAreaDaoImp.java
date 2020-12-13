package com.tuozuo.tavern.organ.biz.dao.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tuozuo.tavern.organ.biz.dao.CompanyNameAreaDao;
import com.tuozuo.tavern.organ.biz.mapper.CompanyNameAreaMapper;
import com.tuozuo.tavern.organ.biz.model.CompanyNameArea;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/12/13 <br>
 */
@Repository
public class CompanyNameAreaDaoImp extends ServiceImpl<CompanyNameAreaMapper, CompanyNameArea> implements CompanyNameAreaDao {
    @Override
    public void insertBatch(List<CompanyNameArea> companyNameAreas) {
        this.saveBatch(companyNameAreas);
    }

    @Override
    public List<CompanyNameArea> selectAll() {
        return this.list();
    }
}
