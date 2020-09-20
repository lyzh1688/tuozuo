package com.tuozuo.tavern.xinruyi.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tuozuo.tavern.xinruyi.model.ProjectPayment;
import com.tuozuo.tavern.xinruyi.model.ProjectPaymentSnapshot;
import com.tuozuo.tavern.xinruyi.model.StaffSalaryInfo;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/9/19 <br>
 */
public interface PaymentInfoDao {

    IPage<ProjectPayment> selectPaymentList(int pageNo, int pageSize, String companyId, String projectId, String status);

    IPage<ProjectPayment> selectPaymentHisList(int pageNo, int pageSize, String companyId, String projectId, String beginMonth, String endMonth);

    void savePaymentInfo(ProjectPayment projectPayment);

    void mergePaymentSnapshot(ProjectPaymentSnapshot projectPaymentSnapshot);

    void savePaymentDetailInitial(String companyId,
                                  String month,
                                  String payDate,
                                  String paymentId,
                                  String projectId);

    void updatePaymentInfo(ProjectPayment projectPayment);

    IPage<StaffSalaryInfo> selectStaffDetail(int pageNo, int pageSize,
                                             String companyId,
                                             String paymentId,
                                             String projectId,
                                             String startDate,
                                             String endDate,
                                             String staffId);

}
