package com.tuozuo.tavern.xinruyi.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tuozuo.tavern.common.protocol.UserTypeDict;
import com.tuozuo.tavern.xinruyi.dao.EventInfoDao;
import com.tuozuo.tavern.xinruyi.dao.PaymentInfoDao;
import com.tuozuo.tavern.xinruyi.dao.ProjectInfoDao;
import com.tuozuo.tavern.xinruyi.dict.EventType;
import com.tuozuo.tavern.xinruyi.dict.PaymentStatus;
import com.tuozuo.tavern.xinruyi.model.*;
import com.tuozuo.tavern.xinruyi.service.PaymentInfoService;
import com.tuozuo.tavern.xinruyi.utils.DateUtils;
import com.tuozuo.tavern.xinruyi.utils.FileUtils;
import com.tuozuo.tavern.xinruyi.utils.UUIDUtil;
import com.tuozuo.tavern.xinruyi.vo.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/9/19 <br>
 */
@Service
public class PaymentInfoServiceImpl implements PaymentInfoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentInfoServiceImpl.class);

    @Value("${xinruyi.file.url.path:http://119.3.19.171/xinruyi/file/project/file/payment/}")
    private String fileUrlPath;
    @Value("${xinruyi.project.file.path:/mnt/file/xinruyi/project/file/payment/}")
    private String filePath;

    @Autowired
    private PaymentInfoDao paymentInfoDao;

    @Autowired
    private EventInfoDao eventInfoDao;

    @Autowired
    private ProjectInfoDao projectInfoDao;

    @Override
    public IPage<ProjectPayment> queryProjectPaymentList(PaymentListVO vo) {
        return this.paymentInfoDao.selectPaymentList(vo.getPageNo(), vo.getPageSize(), vo.getCompanyId(), vo.getProjectId(), vo.getStatus());
    }

    @Transactional
    @Override
    public void uploadVoucher(PaymentVoucherUploadVO uploadVO, String roleGroup, String companyId) throws Exception {
        String paymentId = UUIDUtil.randomUUID32();
        String voucher = this.storePaymentVoucherFile(uploadVO.getProjectId(), paymentId, uploadVO.getVoucher());
        if (roleGroup.equals(UserTypeDict.custom)) {
            //1、上传凭证
            //2、刷detail初始值
            //3、发起申请事件
            ProjectPaymentSnapshot snapshot = new ProjectPaymentSnapshot();
            snapshot.setCompanyId(companyId);
            snapshot.setPaymentId(paymentId);
            snapshot.setProjectId(uploadVO.getProjectId());

            ProjectPayment projectPayment = new ProjectPayment();
            projectPayment.setPaymentId(paymentId);
            projectPayment.setCompanyId(companyId);
            projectPayment.setProjectId(uploadVO.getProjectId());
            projectPayment.setPeriod(uploadVO.getMonth());
            projectPayment.setFileVoucher(voucher);
            projectPayment.setStatus(PaymentStatus.APPLYING.getStatus());
            projectPayment.setTotalSalary(uploadVO.getAmount());
            projectPayment.setPayDate(DateUtils.parseDate(uploadVO.getPayDate(), DateUtils.DEFAULT_SIMPLE_8__FORMATTER));
            projectPayment.setUpdateDate(LocalDateTime.now());


            EventTodoList eventTodoList = new EventTodoList();
            //构造company_id,project_id,company_name,project_name,amount,month,payment_id,transferVoucher
            ProjectInfo projectInfo = this.projectInfoDao.selectProjectInfo(uploadVO.getProjectId());
            JSONObject eventSnapshot = new JSONObject();
            eventSnapshot.put("company_id", companyId);
            eventSnapshot.put("project_id", uploadVO.getProjectId());
            eventSnapshot.put("company_name", projectInfo.getCompanyName());
            eventSnapshot.put("project_name", projectInfo.getProjectName());
            eventSnapshot.put("amount", uploadVO.getAmount());
            eventSnapshot.put("month", uploadVO.getMonth());
            eventSnapshot.put("payment_id", paymentId);
            eventSnapshot.put("transferVoucher", voucher);

            eventTodoList.setSnapshot(JSON.toJSONString(eventSnapshot));
            eventTodoList.setEventOwnerId(companyId);
            eventTodoList.setApplicant(projectInfo.getCompanyName());
            eventTodoList.setEventId(UUIDUtil.randomUUID32());
            eventTodoList.setEventType(EventType.SALARY_RELEASE_APPLY.getStatus());
            eventTodoList.setRole(UserTypeDict.staff);
            eventTodoList.setEventOwnerName(projectInfo.getCompanyName());
            eventTodoList.setEventDate(LocalDateTime.now());
            eventTodoList.setProjectId(projectInfo.getProjectId());
            eventTodoList.setCompanyId(companyId);

            this.eventInfoDao.insertEventTodo(eventTodoList);
            this.paymentInfoDao.mergePaymentSnapshot(snapshot);
            this.paymentInfoDao.savePaymentInfo(projectPayment);
            this.paymentInfoDao.savePaymentDetailInitial(companyId, uploadVO.getMonth(), uploadVO.getPayDate(), paymentId, uploadVO.getProjectId());


        } else {
            //1、上传发放凭证
            //2、更新发放状态
            //3、结束发放流程
            ProjectPayment projectPayment = this.paymentInfoDao.selectById(uploadVO.getPaymentId());
            projectPayment.setFileVoucher(voucher);
            projectPayment.setStatus(PaymentStatus.RELEASED.getStatus());
            projectPayment.setUpdateDate(LocalDateTime.now());

            this.paymentInfoDao.updatePaymentInfo(projectPayment);
            EventTodoList eventTodoList = this.eventInfoDao.selectProjectTodo(projectPayment.getProjectId(), EventType.SALARY_RELEASE.getStatus());
            EventFinishList eventFinishList = new EventFinishList();
            BeanUtils.copyProperties(eventTodoList, eventFinishList);
            eventFinishList.setUpdateDate(LocalDateTime.now());
            eventFinishList.setStatus("1");
            this.eventInfoDao.delEventTodo(eventTodoList.getEventId());
            this.eventInfoDao.insertEventFinish(eventFinishList);

        }

    }

    @Override
    public IPage<ProjectPayment> queryProjectPaymentHisList(PaymentHistoryVO vo) {
        return this.paymentInfoDao.selectPaymentHisList(vo.getPageNo(), vo.getPageSize(), vo.getCompanyId(), vo.getProjectId(), vo.getBeginMonth(), vo.getEndMonth());
    }

    @Override
    public IPage<StaffSalaryInfo> queryStaffDetail(StaffSalaryDetailVO vo) {
        return this.paymentInfoDao.selectStaffDetail(vo.getPageNo(), vo.getPageSize(), vo.getCompanyId(), vo.getPaymentId(), vo.getProjectId(), vo.getStartDate(), vo.getEndDate(), vo.getStaffId());
    }

    @Override
    public IPage<StaffSalaryInfo> queryStaffInfo(StaffSalaryInfoVO vo) {
        return this.paymentInfoDao.selectStaffInfo(vo.getPageNo(), vo.getPageSize(), vo.getProjectId());
    }

    @Transactional
    @Override
    public void modifyStaffPayment(StaffPaymentInfoVO vo) {

        List<ProjectPaymentDetail> projectPaymentDetailList = vo.getStaffSalary()
                .stream()
                .map(info -> {
                    ProjectPaymentDetail detail = new ProjectPaymentDetail();
                    detail.setCompanyId(vo.getCompanyId());
                    detail.setPayDate(vo.getPayDate());
                    detail.setPaymentId(vo.getPaymentId());
                    detail.setPeriod(vo.getPeriod());
                    detail.setProjectId(vo.getProjectId());
                    detail.setStaffId(info.getStaffId());
                    detail.setSalary(info.getSalary());
                    return detail;
                }).collect(Collectors.toList());

        this.paymentInfoDao.delStaffPaymentById(vo.getPaymentId());
        this.paymentInfoDao.saveStaffPaymentList(projectPaymentDetailList);
    }

    @Transactional
    @Override
    public void staffPaymentConfirm(StaffPaymentConfirmVO vo) {
        //1、工资发放状态改变
        //2、删除工资申请
        //3、工资申请完成
        //4、发起
        ProjectPayment projectPayment = this.paymentInfoDao.selectById(vo.getPaymentId());
        if (vo.getStatus().equals("1")) {
            projectPayment.setStatus(PaymentStatus.TO_PAYOFF.getStatus());
        } else {
            projectPayment.setStatus(PaymentStatus.FAILED.getStatus());
        }

        EventTodoList eventTodoList = this.eventInfoDao.selectProjectTodo(projectPayment.getProjectId(), EventType.SALARY_RELEASE_CONFIRM.getStatus());
        EventFinishList eventFinishList = new EventFinishList();
        BeanUtils.copyProperties(eventTodoList, eventFinishList);
        eventFinishList.setUpdateDate(LocalDateTime.now());
        eventFinishList.setStatus(vo.getStatus());
        this.eventInfoDao.delEventTodo(eventTodoList.getEventId());
        this.eventInfoDao.insertEventFinish(eventFinishList);
        this.paymentInfoDao.updatePaymentInfo(projectPayment);

        //发起客户审核
        EventTodoList salaryReleaseEvent = new EventTodoList();
        //构造company_id,project_id,company_name,project_name,amount,month,payment_id,transferVoucher
        BeanUtils.copyProperties(eventTodoList, salaryReleaseEvent);
        salaryReleaseEvent.setEventId(UUIDUtil.randomUUID32());
        salaryReleaseEvent.setEventType(EventType.SALARY_RELEASE.getStatus());
        salaryReleaseEvent.setRole(UserTypeDict.staff);
        salaryReleaseEvent.setEventDate(LocalDateTime.now());
        this.eventInfoDao.insertEventTodo(salaryReleaseEvent);


    }

    @Transactional
    @Override
    public void staffPaymentAudit(StaffPaymentAuditVO vo) {
        //工资发放审核
        //1、审核状态
        //2、删除工资申请
        //3、工资申请完成
        //4、发起客户审核

        ProjectPayment projectPayment = this.paymentInfoDao.selectById(vo.getPaymentId());
        if (vo.getStatus().equals("1")) {
            projectPayment.setStatus(PaymentStatus.TO_PAYOFF.getStatus());
        } else {
            projectPayment.setStatus(PaymentStatus.FAILED.getStatus());
        }

        EventTodoList eventTodoList = this.eventInfoDao.selectProjectTodo(projectPayment.getProjectId(), EventType.SALARY_RELEASE_APPLY.getStatus());
        EventFinishList eventFinishList = new EventFinishList();
        BeanUtils.copyProperties(eventTodoList, eventFinishList);
        eventFinishList.setUpdateDate(LocalDateTime.now());
        eventFinishList.setStatus(vo.getStatus());
        this.eventInfoDao.delEventTodo(eventTodoList.getEventId());
        this.eventInfoDao.insertEventFinish(eventFinishList);
        this.paymentInfoDao.updatePaymentInfo(projectPayment);

        //发起客户审核
        EventTodoList eventConfirmTodo = new EventTodoList();
        //构造company_id,project_id,company_name,project_name,amount,month,payment_id,transferVoucher
        BeanUtils.copyProperties(eventTodoList, eventConfirmTodo);
        eventConfirmTodo.setEventId(UUIDUtil.randomUUID32());
        eventConfirmTodo.setEventType(EventType.SALARY_RELEASE_CONFIRM.getStatus());
        eventConfirmTodo.setRole(UserTypeDict.custom);
        eventConfirmTodo.setEventDate(LocalDateTime.now());
        this.eventInfoDao.insertEventTodo(eventConfirmTodo);

    }


    //path + projectId + paymentId +file
    private String storePaymentVoucherFile(String projectId, String paymentId, MultipartFile file) throws Exception {

        String pathLocation = StringUtils.join(filePath, projectId,
                "/", paymentId, "/");
        String fileName = FileUtils.multiPartFileWriter(file, pathLocation);
        return StringUtils.join(fileUrlPath, projectId,
                "/", paymentId, "/", fileName);

    }
}
