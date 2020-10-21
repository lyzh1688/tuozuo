package com.tuozuo.tavern.xinruyi.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tuozuo.tavern.common.protocol.UserTypeDict;
import com.tuozuo.tavern.xinruyi.convert.ModelConverterFactory;
import com.tuozuo.tavern.xinruyi.convert.ModelMapConverterFactory;
import com.tuozuo.tavern.xinruyi.dao.*;
import com.tuozuo.tavern.xinruyi.dict.EventType;
import com.tuozuo.tavern.xinruyi.dict.ProjectStatus;
import com.tuozuo.tavern.xinruyi.dict.StaffStatus;
import com.tuozuo.tavern.xinruyi.dto.ProjectExperienceDetailDTO;
import com.tuozuo.tavern.xinruyi.dto.ProjectExperiencePaymentDTO;
import com.tuozuo.tavern.xinruyi.model.*;
import com.tuozuo.tavern.xinruyi.service.ProjectInfoService;
import com.tuozuo.tavern.xinruyi.utils.DateUtils;
import com.tuozuo.tavern.xinruyi.utils.FileUtils;
import com.tuozuo.tavern.xinruyi.utils.UUIDUtil;
import com.tuozuo.tavern.xinruyi.utils.ValidateUtils;
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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/8/29 <br>
 */
@Service
public class ProjectInfoServiceImpl implements ProjectInfoService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProjectInfoServiceImpl.class);

    @Value("${xinruyi.file.url.path:http://119.3.19.171/xinruyi/file/project/file/}")
    private String fileUrlPath;
    @Value("${xinruyi.project.file.path:/mnt/file/xinruyi/project/file/}")
    private String filePath;

    @Autowired
    private ProjectInfoDao projectInfoDao;
    @Autowired
    private ProjectStaffInfoDao projectStaffInfoDao;
    @Autowired
    private EventInfoDao eventInfoDao;
    @Autowired
    private CompanyInfoDao companyInfoDao;
    @Autowired
    private StaffInfoDao staffInfoDao;
    @Autowired
    private ModelMapConverterFactory converter;
    @Autowired
    private PaymentInfoDao paymentInfoDao;
    @Autowired
    private WorkerInfoDao workerInfoDao;


    @Override
    public List<ProjectInfo> queryProjectInfo(String companyId, String projectName, int queryCnt, Boolean all, String roleGroup) {
        if (roleGroup.equals(UserTypeDict.custom)) {

            if (all) {
                return this.projectInfoDao.selectAllCustomProjectInfo(companyId, projectName);
            } else {
                return this.projectInfoDao.selectCustomProjectInfo(companyId, projectName, queryCnt);
            }

        } else {
            if (all) {
                return this.projectInfoDao.selectAllProjectInfo(projectName);
            } else {
                return this.projectInfoDao.selectProjectInfo(projectName, queryCnt);
            }

        }
    }

    @Override
    public IPage<ProjectStaffInfo> queryProjectStaffInfo(int pageNo, int pageSize, String companyId, String projectId) {
        return this.projectStaffInfoDao.selectProjectStaffInfo(pageNo, pageSize, companyId, projectId);
    }

    @Override
    public void addProjectStaff(ProjectStaff projectStaff) {
        this.projectStaffInfoDao.insertProjectStaff(projectStaff);
    }

    @Override
    public void modifyProjectStaff(ProjectStaff projectStaff) throws Exception {

        LocalDate quitDate = projectStaff.getQuitDate();
        if (quitDate != null && quitDate.getMonth().equals(LocalDate.now().getMonth())) {
            throw new Exception("当月不可离团，请重新选择离团日期!");
        }
        this.projectStaffInfoDao.updateProjectStaff(projectStaff);
    }

    @Transactional
    @Override
    public void delProjectStaff(String projectId, String staffId, String companyId) throws Exception {

        if (this.isRepeatedStaffFiredApply(companyId, projectId, staffId, EventType.STAFF_FIRE.getStatus())) {
            LOGGER.error("[项目人员裁员申请] repeated apply companyId: {},projectId: {},staffId: {}", companyId, projectId, staffId);
            return;
        }


        ProjectStaff projectStaff = new ProjectStaff();
        projectStaff.setProjectId(projectId);
        projectStaff.setStaffId(staffId);
        projectStaff.setStatus(StaffStatus.FROZEN.getStatus());
        this.projectStaffInfoDao.delProjectStaff(projectStaff);
        //裁员变动事件
        CompanyInfo companyInfo = this.companyInfoDao.selectCompanyInfo(companyId);
        EventTodoList eventTodoList = new EventTodoList();
        //构造snapshot companyName，projectName,staffName,staffContact,payStatus,remark,auditResult
        StaffBasicInfo staffBasicInfo = this.projectStaffInfoDao.selectStaff(projectId, staffId);
        eventTodoList.setSnapshot(JSON.toJSONString(staffBasicInfo));
        eventTodoList.setEventOwnerId(companyId);
        eventTodoList.setApplicant(companyInfo.getCompanyName());
        eventTodoList.setEventId(UUIDUtil.randomUUID32());
        eventTodoList.setEventType(EventType.STAFF_FIRE.getStatus());
        eventTodoList.setRole(UserTypeDict.staff);
        eventTodoList.setEventOwnerName(companyInfo.getCompanyName());
        eventTodoList.setEventDate(LocalDateTime.now());
        eventTodoList.setProjectId(projectId);
        eventTodoList.setCompanyId(companyId);
        eventTodoList.setStaffId(staffId);
        eventTodoList.setRegisterId(companyInfo.getRegisterId());
        this.eventInfoDao.insertEventTodo(eventTodoList);

    }

    @Override
    public IPage<ProjectInfo> queryProjectInfo(ProjectListVo vo, String companyId) {
        String downLimitBudget = null, upperLimitBudget = null;
        if (StringUtils.isNoneEmpty(vo.getBudget())) {
            downLimitBudget = StringUtils.substringBefore(vo.getBudget(), "~");
            upperLimitBudget = StringUtils.substringAfter(vo.getBudget(), "~");
            if (ValidateUtils.isContainChinese(upperLimitBudget)) {
                upperLimitBudget = null;
            }
        }
        return this.projectInfoDao.selectProjectPage(vo.getPageNo(),
                vo.getPageSize(),
                companyId,
                vo.getProjectId(),
                vo.getIndustryType(),
                downLimitBudget,
                upperLimitBudget,
                vo.getRequirementStatus(),
                vo.getBeginDate(),
                vo.getEndDate());

    }

    @Transactional
    @Override
    public void addProjectInfo(ProjectAddVO vo, String companyId) throws Exception {

        if (this.isRepeatedApply(companyId, vo.getProjectName())) {
            LOGGER.error("[项目申请] repeated apply companyId: {},applyVO: {}", companyId, JSON.toJSONString(vo));
            return;
        }


        ProjectInfo projectInfo = ModelConverterFactory.addVoToProjectInfo(vo, companyId);
        this.setProjectInfoFiles(vo.getProjectFile(), projectInfo);
        this.projectInfoDao.insertProject(projectInfo);

        CompanyInfo companyInfo = this.companyInfoDao.selectCompanyInfo(companyId);
        //项目发布事件
        EventTodoList eventTodoList = new EventTodoList();
        //构造snapshot
        JSONObject snapshot = new JSONObject();
        snapshot.put("projectId", projectInfo.getProjectId());
        eventTodoList.setSnapshot(snapshot.toJSONString());
        eventTodoList.setEventOwnerId(companyId);
        eventTodoList.setApplicant(companyInfo.getCompanyName());
        eventTodoList.setEventId(UUIDUtil.randomUUID32());
        eventTodoList.setEventType(EventType.PROJECT_RELEASE.getStatus());
        eventTodoList.setRole(UserTypeDict.staff);
        eventTodoList.setEventOwnerName(companyInfo.getCompanyName());
        eventTodoList.setEventDate(LocalDateTime.now());
        eventTodoList.setProjectId(projectInfo.getProjectId());
        eventTodoList.setCompanyId(companyId);
        eventTodoList.setRegisterId(companyInfo.getRegisterId());
        this.eventInfoDao.insertEventTodo(eventTodoList);

    }

    @Override
    public void modifyProjectInfo(ProjectModifyVO vo, String companyId) throws Exception {

        ProjectInfo projectInfo = this.projectInfoDao.selectProjectInfo(vo.getProjectId());
        if (projectInfo.getStatus().equals(ProjectStatus.AUDITED.getStatus()) || projectInfo.getStatus().equals(ProjectStatus.DONE.getStatus())) {
            throw new Exception("项目已完成审核，修改失败");
        }
//        String today = DateUtils.formatDate(LocalDate.now(), DateUtils.SIMPLE_8_FORMATTER);
        ModelConverterFactory.modifyVoToProjectInfo(vo, companyId, projectInfo);
        this.setProjectInfoFiles(vo.getProjectFile(), projectInfo);
        this.projectInfoDao.modifyProject(projectInfo);

        if (projectInfo.getStatus().equals(ProjectStatus.APPLY_FAILURE.getStatus())) {
            CompanyInfo companyInfo = this.companyInfoDao.selectCompanyInfo(companyId);
            //项目发布事件
            EventTodoList eventTodoList = new EventTodoList();
            //构造snapshot
            JSONObject snapshot = new JSONObject();
            snapshot.put("projectId", projectInfo.getProjectId());
            eventTodoList.setSnapshot(snapshot.toJSONString());
            eventTodoList.setEventOwnerId(companyId);
            eventTodoList.setApplicant(companyInfo.getCompanyName());
            eventTodoList.setEventId(UUIDUtil.randomUUID32());
            eventTodoList.setEventType(EventType.PROJECT_RELEASE.getStatus());
            eventTodoList.setRole(UserTypeDict.staff);
            eventTodoList.setEventOwnerName(companyInfo.getCompanyName());
            eventTodoList.setEventDate(LocalDateTime.now());
            eventTodoList.setProjectId(projectInfo.getProjectId());
            eventTodoList.setCompanyId(companyId);
            eventTodoList.setRegisterId(companyInfo.getRegisterId());
            this.eventInfoDao.insertEventTodo(eventTodoList);
            projectInfo.setStatus(ProjectStatus.APPLYING.getStatus());
            this.projectInfoDao.modifyProject(projectInfo);
        }

    }

    @Transactional
    @Override
    public void endProject(String project, String companyId) {
        if (this.isRepeatedAuthApply(project, companyId, EventType.PROJECT_DONE.getStatus())) {
            LOGGER.error("[项目完结申请] repeated apply companyId: {},projectId: {}", companyId, project);
            return;
        }


        ProjectInfo projectInfo = new ProjectInfo();
        projectInfo.setProjectId(project);
        projectInfo.setStatus(ProjectStatus.AUDITED.getStatus());
        this.projectInfoDao.modifyProject(projectInfo);

        CompanyInfo companyInfo = this.companyInfoDao.selectCompanyInfo(companyId);
        //项目完成事件确认
        EventTodoList eventTodoList = new EventTodoList();
        //构造snapshot
        JSONObject snapshot = new JSONObject();
        snapshot.put("projectId", projectInfo.getProjectId());
        eventTodoList.setSnapshot(snapshot.toJSONString());
        eventTodoList.setEventOwnerId(companyId);
        eventTodoList.setApplicant(companyInfo.getCompanyName());
        eventTodoList.setEventId(UUIDUtil.randomUUID32());
        eventTodoList.setEventType(EventType.PROJECT_DONE.getStatus());
        eventTodoList.setRole(UserTypeDict.staff);
        eventTodoList.setEventOwnerName(companyInfo.getCompanyName());
        eventTodoList.setEventDate(LocalDateTime.now());
        eventTodoList.setProjectId(projectInfo.getProjectId());
        eventTodoList.setCompanyId(companyId);
        eventTodoList.setRegisterId(companyInfo.getRegisterId());
        this.eventInfoDao.insertEventTodo(eventTodoList);
    }

    @Override
    public ProjectInfo queryProjectDetail(String projectId) {
        return this.projectInfoDao.selectProjectInfo(projectId);
    }

    @Override
    public IPage<ProjectEventInfo> queryProjectEvents(ProjectEventVO vo) {
        return this.eventInfoDao.selectProjects(vo.getPageNo(), vo.getPageSize(), vo.getCompanyId(), vo.getProjectId(), vo.getStatus(), vo.getIndustryType(), vo.getBeginDate(), vo.getEndDate());
    }

    @Transactional
    @Override
    public void auditProjectRelease(AuditProjectReleaseVO vo) {
        //1、修改工程状态
        ProjectInfo projectInfo = new ProjectInfo();
        projectInfo.setProjectId(vo.getProjectId());
        projectInfo.setRemark(vo.getRemark());
        projectInfo.setFee(vo.getFee());
        if (vo.getStatus().equals("1")) {
            projectInfo.setStatus(ProjectStatus.APPLY_SUCCESS.getStatus());
        } else {
            projectInfo.setStatus(ProjectStatus.APPLY_FAILURE.getStatus());
        }
        //2、处理事件
        EventTodoList eventTodoList = this.eventInfoDao.selectProjectTodo(vo.getProjectId(), EventType.PROJECT_RELEASE.getStatus());
        EventFinishList eventFinishList = new EventFinishList();
        BeanUtils.copyProperties(eventTodoList, eventFinishList);
        eventFinishList.setUpdateDate(LocalDateTime.now());
        eventFinishList.setStatus(vo.getStatus());
        this.eventInfoDao.delEventTodo(eventTodoList.getEventId());
        this.eventInfoDao.insertEventFinish(eventFinishList);
        this.projectInfoDao.modifyProject(projectInfo);

    }

    @Transactional
    @Override
    public void auditProjectDone(AuditProjectDoneVO vo) {
        //1、修改工程状态
        ProjectInfo projectInfo = new ProjectInfo();
        projectInfo.setProjectId(vo.getProjectId());
        projectInfo.setRemark(vo.getRemark());
        if (vo.getStatus().equals("1")) {
            projectInfo.setStatus(ProjectStatus.DONE.getStatus());
        } else {
            projectInfo.setStatus(ProjectStatus.APPLY_FAILURE.getStatus());
        }
        //2、处理事件
        EventTodoList eventTodoList = this.eventInfoDao.selectProjectTodo(vo.getProjectId(), EventType.PROJECT_DONE.getStatus());
        EventFinishList eventFinishList = new EventFinishList();
        BeanUtils.copyProperties(eventTodoList, eventFinishList);
        eventFinishList.setUpdateDate(LocalDateTime.now());
        eventFinishList.setStatus(vo.getStatus());
        this.eventInfoDao.delEventTodo(eventTodoList.getEventId());
        this.eventInfoDao.insertEventFinish(eventFinishList);
        this.projectInfoDao.modifyProject(projectInfo);
    }

    @Transactional
    @Override
    public void auditProjectStaffFired(AuditStaffFiredVO vo) throws Exception {
        ProjectStaff projectStaff = new ProjectStaff();
        projectStaff.setStaffId(vo.getStaffId());
        projectStaff.setProjectId(vo.getProjectId());
        projectStaff.setIsSettled(vo.getPayStatus());
        projectStaff.setRemark(vo.getRemark());
        if (vo.getAuditResult().equals("1")) {
            projectStaff.setStatus(StaffStatus.LEAVE.getStatus());
        } else {
            projectStaff.setStatus(StaffStatus.IN_SERVICE.getStatus());
        }
        this.projectStaffInfoDao.updateProjectStaff(projectStaff);

        //2、处理事件
        Optional<EventTodoList> op = this.eventInfoDao.selectStaffFiredTodo(vo.getProjectId(), vo.getStaffId(), EventType.STAFF_FIRE.getStatus());
        if (!op.isPresent()) {
            throw new Exception("该成员不存在");
        }
        EventTodoList eventTodoList = op.get();
        EventFinishList eventFinishList = new EventFinishList();
        BeanUtils.copyProperties(eventTodoList, eventFinishList);
        eventFinishList.setUpdateDate(LocalDateTime.now());
        eventFinishList.setStatus(vo.getAuditResult());
        this.eventInfoDao.delEventTodo(eventTodoList.getEventId());
        this.eventInfoDao.insertEventFinish(eventFinishList);
    }

    @Override
    public void modifyProjectStatusTask() {
        this.projectInfoDao.updateStatus();
    }

    @Override
    public boolean isRepeatedApply(String companyId, String projectName) {
        return this.projectInfoDao.selectProjectInfo(companyId, projectName).isPresent();
    }

    @Override
    public boolean isRepeatedAuthApply(String companyId, String projectId, String eventType) {
        return this.eventInfoDao.selectProjectFinishTodo(companyId, projectId, eventType).isPresent();
    }

    @Override
    public boolean isRepeatedStaffFiredApply(String companyId, String projectId, String staffId, String eventType) {
        return this.eventInfoDao.selectProjectStaffFiredTodo(companyId, projectId, staffId, eventType).isPresent();
    }

    @Override
    public List<HotProjectInfo> queryHotProjects() {
        return this.projectInfoDao.selectHotProjects();
    }

    @Override
    public List<IndustryProjectInfo> queryIndustryProject(String projectId, String publishDate, String industryId) {
        if (StringUtils.isEmpty(publishDate)) {
            publishDate = DateUtils.formatDate(LocalDate.now().plusYears(1), DateUtils.DEFAULT_SIMPLE_8__FORMATTER);
        }
        return this.projectInfoDao.selectIndustryProjectById(projectId, publishDate, industryId);
    }

    @Override
    public List<IndustryProjectInfo> queryIndustryProjectByName(String projectName,
                                                                String publishDate,
                                                                String projectId) {
        if (StringUtils.isEmpty(publishDate)) {
            publishDate = DateUtils.formatDate(LocalDate.now().plusYears(1), DateUtils.DEFAULT_SIMPLE_8__FORMATTER);
        }
        return this.projectInfoDao.selectIndustryProjectByName(projectName, publishDate, projectId);
    }

    @Override
    public List<ProjectInfo> queryExperienceProjects(String projectId, String publishDate, String registerId, String status) {
        if (StringUtils.isEmpty(publishDate)) {
            publishDate = DateUtils.formatDate(LocalDate.now().plusYears(1), DateUtils.DEFAULT_SIMPLE_8__FORMATTER);
        }
        if (status.equals("0")) {
            return this.projectInfoDao.selectFinishedProjects(projectId, publishDate, registerId).stream()
                    .map(projectInfo -> {
                        long workDays = this.calHisWorkDays(projectInfo.getEnterDate(), projectInfo.getQuitDate(), projectInfo.getPublishDate(), projectInfo.getPeriod());
                        projectInfo.setWorkDays(workDays);
                        return projectInfo;
                    }).collect(Collectors.toList());
        } else {
            return this.projectInfoDao.selectCurProjects(projectId, publishDate, registerId).stream()
                    .map(projectInfo -> {
                        long workDays = this.calCurWorkDays(projectInfo.getEnterDate());
                        projectInfo.setWorkDays(workDays);
                        return projectInfo;
                    }).collect(Collectors.toList());
        }
    }

    @Override
    public ProjectExperienceDetailDTO queryProjectExperienceDetail(String registerId, String projectId, String paymentId, String payDate) {
        ProjectInfo projectInfo = this.projectInfoDao.selectProjectInfo(projectId);
        if (StringUtils.isEmpty(payDate)) {
            payDate = DateUtils.formatDate(LocalDate.now().plusYears(1), DateUtils.DEFAULT_SIMPLE_8__FORMATTER);
        }
        List<ProjectPaymentDetail> projectPaymentDetails = this.paymentInfoDao.selectPaymentRecord(registerId, projectId, paymentId, payDate);
        ProjectExperienceDetailDTO detailDTO = new ProjectExperienceDetailDTO();
        detailDTO = this.converter.modelToProjectExperienceDetailDTO(projectInfo);
        List<ProjectExperiencePaymentDTO> paymentDTOS = projectPaymentDetails
                .stream()
                .map(this.converter::modelToProjectExperiencePaymentDTO)
                .collect(Collectors.toList());
        detailDTO.setPayment(paymentDTOS);
        return detailDTO;
    }

    @Override
    public void applyForProject(ProjectParticipateVO vo) throws Exception {
        WorkerInfo workerInfo = this.workerInfoDao.selectById(vo.getRegisterId());
        if (workerInfo == null) {
            throw new Exception("您尚未认证，无法加入项目");
        }
        StaffResourcePool staffResourcePool = this.staffInfoDao.selectStaffInfo(vo.getCompanyId(), workerInfo.getIdNumber());
        //发布加入项目申请
        EventTodoList eventTodoList = new EventTodoList();
        //构造snapshot
        // 公司ID，name
        //项目id，name
        //staffId，name
        //联系方式
        JSONObject snapshot = new JSONObject();
        snapshot.put("companyId", vo.getCompanyId());
        snapshot.put("companyName", vo.getCompanyName());
        snapshot.put("projectId", vo.getProjectId());
        snapshot.put("projectName", vo.getProjectName());
        if(staffResourcePool != null){
            snapshot.put("staffId", staffResourcePool.getStaffId());
        }
        snapshot.put("staffName", workerInfo.getName());
        snapshot.put("contact", workerInfo.getContact());
        eventTodoList.setSnapshot(snapshot.toJSONString());
        eventTodoList.setEventOwnerId(vo.getRegisterId());
        eventTodoList.setApplicant(workerInfo.getName());
        eventTodoList.setEventId(UUIDUtil.randomUUID32());
        eventTodoList.setEventType(EventType.STAFF_JOIN.getStatus());
        eventTodoList.setRole(UserTypeDict.staff);
        eventTodoList.setEventOwnerName(workerInfo.getName());
        eventTodoList.setEventDate(LocalDateTime.now());
        eventTodoList.setProjectId(vo.getProjectId());
        eventTodoList.setCompanyId(vo.getCompanyId());
        eventTodoList.setRegisterId(vo.getRegisterId());
        if(staffResourcePool != null){
            eventTodoList.setStaffId(staffResourcePool.getStaffId());
        }
        this.eventInfoDao.insertEventTodo(eventTodoList);


    }

    @Transactional
    @Override
    public void auditWorkerParticipation(AuditWorkerVO vo) throws Exception {
        //1、处理事件
        EventTodoList eventTodoList = this.eventInfoDao.selectWorkerTodo(vo.getRegisterId(), EventType.STAFF_JOIN.getStatus());
        EventFinishList eventFinishList = new EventFinishList();
        BeanUtils.copyProperties(eventTodoList, eventFinishList);
        JSONObject snapshot = JSON.parseObject(eventTodoList.getSnapshot());
        snapshot.put("remark", vo.getRemark());
        eventFinishList.setSnapshot(JSON.toJSONString(snapshot));
        eventFinishList.setUpdateDate(LocalDateTime.now());
        eventFinishList.setStatus(vo.getResult());
        this.eventInfoDao.delEventTodo(eventTodoList.getEventId());
        this.eventInfoDao.insertEventFinish(eventFinishList);

        //2、绑定关系
        if(vo.getResult().equals("1")){
            WorkerInfo workerInfo = this.workerInfoDao.selectById(vo.getRegisterId());
            StaffResourcePool staffResourcePool = this.staffInfoDao.selectStaffInfo(snapshot.getString("companyId"), workerInfo.getIdNumber());
            if(staffResourcePool == null){
                throw new Exception("该员工未加入员工池，无法审核通过，请先添加员工！");
            }
            WorkerStaffRel rel = new WorkerStaffRel();
            rel.setStaffId(staffResourcePool.getStaffId());
            rel.setRegisterId(vo.getRegisterId());
            Optional<WorkerStaffRel> op =  this.workerInfoDao.selectWorkerStaffRelById(rel.getRegisterId(),rel.getStaffId());
            if(!op.isPresent()){
                LOGGER.info("【小程序】[绑定公司和员工关系]: 员工Id,[{}],openId,[{}]",rel.getStaffId(),rel.getRegisterId());
                this.workerInfoDao.insertStaffRel(rel);
            }
        }

    }


    private void setProjectInfoFiles(MultipartFile file, ProjectInfo projectInfo) throws Exception {
        if (file != null) {
            String projectFileUrl = this.storeProjectFile(projectInfo.getProjectId(), file);
            LOGGER.info("projectFileUrl: {}", projectFileUrl);
            projectInfo.setFileMaterial(projectFileUrl);
        }

    }

    //path + projectId + file
    private String storeProjectFile(String projectId, MultipartFile file) throws Exception {

        String pathLocation = StringUtils.join(filePath, projectId,
                "/");
        String fileName = FileUtils.multiPartFileWriter(file, pathLocation);
        return StringUtils.join(fileUrlPath, projectId,
                "/", fileName);

    }


    private long calCurWorkDays(LocalDate enterDate) {
        return LocalDate.now().toEpochDay() - enterDate.toEpochDay();
    }

    private long calHisWorkDays(LocalDate enterDate, LocalDate quitDate, LocalDate publishDate, BigDecimal period) {
        if (quitDate != null) {
            return quitDate.toEpochDay() - enterDate.toEpochDay();
        }
        LocalDate finishDate = publishDate.plusDays(period.multiply(new BigDecimal(30)).intValue());
        return finishDate.toEpochDay() - enterDate.toEpochDay();
    }
}
