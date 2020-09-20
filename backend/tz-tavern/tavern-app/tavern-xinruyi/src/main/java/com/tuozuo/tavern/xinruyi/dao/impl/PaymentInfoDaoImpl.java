package com.tuozuo.tavern.xinruyi.dao.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tuozuo.tavern.xinruyi.dao.PaymentInfoDao;
import com.tuozuo.tavern.xinruyi.mapper.ProjectPaymentDetailMapper;
import com.tuozuo.tavern.xinruyi.mapper.ProjectPaymentMapper;
import com.tuozuo.tavern.xinruyi.mapper.ProjectPaymentSnapshotMapper;
import com.tuozuo.tavern.xinruyi.model.ProjectPayment;
import com.tuozuo.tavern.xinruyi.model.ProjectPaymentSnapshot;
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
    @Autowired
    private ProjectPaymentSnapshotMapper projectPaymentSnapshotMapper;
    @Autowired
    private ProjectPaymentDetailMapper projectPaymentDetailMapper;

    @Override
    public IPage<ProjectPayment> selectPaymentList(int pageNo, int pageSize, String companyId, String projectId, String status) {
        Page page = new Page(pageNo, pageSize);
        return this.projectPaymentMapper.selectByPage(page, companyId, projectId, status);
    }

    @Override
    public IPage<ProjectPayment> selectPaymentHisList(int pageNo, int pageSize, String companyId, String projectId, String beginMonth, String endMonth) {
        Page page = new Page(pageNo, pageSize);
        return this.projectPaymentMapper.selectHistoryByPage(page, companyId, projectId, beginMonth, endMonth);
    }

    @Override
    public void savePaymentInfo(ProjectPayment projectPayment) {
        this.projectPaymentMapper.insert(projectPayment);
    }

    @Override
    public void mergePaymentSnapshot(ProjectPaymentSnapshot projectPaymentSnapshot) {
        this.projectPaymentSnapshotMapper.insertOrUpdate(projectPaymentSnapshot);
    }

    @Override
    public void savePaymentDetailInitial(String companyId, String month, String payDate, String paymentId, String projectId) {
        this.projectPaymentDetailMapper.insertInitial(companyId, month, payDate, paymentId, projectId);
    }

    @Override
    public void updatePaymentInfo(ProjectPayment projectPayment) {
        this.projectPaymentMapper.updateById(projectPayment);
    }
}
