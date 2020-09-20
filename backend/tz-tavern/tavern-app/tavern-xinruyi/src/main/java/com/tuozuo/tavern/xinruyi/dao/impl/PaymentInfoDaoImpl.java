package com.tuozuo.tavern.xinruyi.dao.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tuozuo.tavern.xinruyi.dao.PaymentInfoDao;
import com.tuozuo.tavern.xinruyi.mapper.ProjectPaymentDetailMapper;
import com.tuozuo.tavern.xinruyi.mapper.ProjectPaymentMapper;
import com.tuozuo.tavern.xinruyi.mapper.ProjectPaymentSnapshotMapper;
import com.tuozuo.tavern.xinruyi.model.ProjectPayment;
import com.tuozuo.tavern.xinruyi.model.ProjectPaymentDetail;
import com.tuozuo.tavern.xinruyi.model.ProjectPaymentSnapshot;
import com.tuozuo.tavern.xinruyi.model.StaffSalaryInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/9/19 <br>
 */
@Repository
public class PaymentInfoDaoImpl extends ServiceImpl<ProjectPaymentDetailMapper, ProjectPaymentDetail> implements PaymentInfoDao {

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

    @Override
    public IPage<StaffSalaryInfo> selectStaffDetail(int pageNo, int pageSize, String companyId, String paymentId, String projectId, String startDate, String endDate, String staffId) {
        Page page = new Page(pageNo, pageSize);
        return this.projectPaymentDetailMapper.selectStaffDetail(page, companyId, paymentId, projectId, startDate, endDate, staffId);
    }

    @Override
    public IPage<StaffSalaryInfo> selectStaffInfo(int pageNo, int pageSize, String projectId) {
        Page page = new Page(pageNo, pageSize);
        return this.projectPaymentDetailMapper.selectStaffInfo(page, projectId);
    }

    @Override
    public void saveStaffPaymentList(List<ProjectPaymentDetail> projectPaymentDetailList) {
        this.saveBatch(projectPaymentDetailList);
    }

    @Override
    public void delStaffPaymentById(String paymentId) {
        this.projectPaymentDetailMapper.delete( Wrappers.<ProjectPaymentDetail>query()
                .lambda()
                .eq(ProjectPaymentDetail::getPaymentId, paymentId));
    }

    @Override
    public ProjectPayment selectById(String paymentId) {
        return this.projectPaymentMapper.selectById(paymentId);
    }
}
