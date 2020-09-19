package com.tuozuo.tavern.xinruyi.dao.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tuozuo.tavern.xinruyi.dao.PaymentInfoDao;
import com.tuozuo.tavern.xinruyi.mapper.ProjectPaymentMapper;
import com.tuozuo.tavern.xinruyi.model.ProjectPayment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/9/19 <br>
 */
@Repository
public class PaymentInfoDaoImpl implements PaymentInfoDao {

    @Autowired
    private ProjectPaymentMapper projectPaymentMapper;

    @Override
    public IPage<ProjectPayment> selectPaymentList(int pageNo, int pageSize, String companyId, String projectId, String status) {
        Page page = new Page(pageNo, pageSize);
        return this.projectPaymentMapper.selectByPage(page, companyId, projectId, status);
    }
}
