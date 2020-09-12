package com.tuozuo.tavern.xinruyi.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tuozuo.tavern.common.protocol.UserTypeDict;
import com.tuozuo.tavern.xinruyi.convert.ModelConverterFactory;
import com.tuozuo.tavern.xinruyi.dao.CompanyInfoDao;
import com.tuozuo.tavern.xinruyi.dao.EventInfoDao;
import com.tuozuo.tavern.xinruyi.dao.ProjectInfoDao;
import com.tuozuo.tavern.xinruyi.dao.ProjectStaffInfoDao;
import com.tuozuo.tavern.xinruyi.dict.EventType;
import com.tuozuo.tavern.xinruyi.dict.ProjectStatus;
import com.tuozuo.tavern.xinruyi.model.*;
import com.tuozuo.tavern.xinruyi.service.ProjectInfoService;
import com.tuozuo.tavern.xinruyi.utils.FileUtils;
import com.tuozuo.tavern.xinruyi.utils.UUIDUtil;
import com.tuozuo.tavern.xinruyi.utils.ValidateUtils;
import com.tuozuo.tavern.xinruyi.vo.ProjectAddVO;
import com.tuozuo.tavern.xinruyi.vo.ProjectEventVO;
import com.tuozuo.tavern.xinruyi.vo.ProjectListVo;
import com.tuozuo.tavern.xinruyi.vo.ProjectModifyVO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

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
        ProjectStaff projectStaff = new ProjectStaff();
        projectStaff.setProjectId(projectId);
        projectStaff.setStaffId(staffId);
        projectStaff.setStatus("1");
        this.projectStaffInfoDao.delProjectStaff(projectStaff);
        //裁员变动事件
        CompanyInfo companyInfo = this.companyInfoDao.selectCompanyInfo(companyId);
        EventTodoList eventTodoList = new EventTodoList();
        //构造snapshot
        JSONObject snapshot = new JSONObject();
        snapshot.put("projectId", projectId);
        snapshot.put("companyId", companyId);
        snapshot.put("staffId", staffId);
        eventTodoList.setSnapshot(snapshot.toJSONString());
        eventTodoList.setEventOwnerId(companyId);
        eventTodoList.setApplicant(companyInfo.getCompanyName());
        eventTodoList.setEventId(UUIDUtil.randomUUID32());
        eventTodoList.setEventType(EventType.STAFF_FIRE.getStatus());
        eventTodoList.setRole(UserTypeDict.custom);
        eventTodoList.setEventOwnerName(companyInfo.getCompanyName());
        eventTodoList.setEventDate(LocalDateTime.now());
        eventTodoList.setProjectId(projectId);
        eventTodoList.setCompanyId(companyId);
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
        eventTodoList.setRole(UserTypeDict.custom);
        eventTodoList.setEventOwnerName(companyInfo.getCompanyName());
        eventTodoList.setEventDate(LocalDateTime.now());
        eventTodoList.setProjectId(projectInfo.getProjectId());
        eventTodoList.setCompanyId(companyId);
        this.eventInfoDao.insertEventTodo(eventTodoList);

    }

    @Override
    public void modifyProjectInfo(ProjectModifyVO vo, String companyId) throws Exception {

        ProjectInfo projectInfo = this.projectInfoDao.selectProjectInfo(vo.getProjectId());
        if (projectInfo.getStatus().equals(ProjectStatus.AUDITED.getStatus()) || projectInfo.getStatus().equals(ProjectStatus.DONE.getStatus())) {
            throw new Exception("项目已完成审核，修改失败");
        }
//        String today = DateUtils.formatDate(LocalDate.now(), DateUtils.SIMPLE_8_FORMATTER);
        projectInfo = ModelConverterFactory.modifyVoToProjectInfo(vo, companyId);
        this.setProjectInfoFiles(vo.getProjectFile(), projectInfo);
        this.projectInfoDao.modifyProject(projectInfo);

    }

    @Transactional
    @Override
    public void endProject(String project) {
        ProjectInfo projectInfo = new ProjectInfo();
        projectInfo.setProjectId(project);
        projectInfo.setStatus(ProjectStatus.AUDITED.getStatus());
        this.projectInfoDao.modifyProject(projectInfo);
        //项目完成事件确认
        EventTodoList eventTodoList = new EventTodoList();
        eventTodoList.setEventType(EventType.PROJECT_DONE.getStatus());
        eventTodoList.setEventDate(LocalDateTime.now());
        eventTodoList.setProjectId(projectInfo.getProjectId());
        this.eventInfoDao.updateEventTodoByProject(eventTodoList);

    }

    @Override
    public ProjectInfo queryProjectDetail(String projectId) {
        return this.projectInfoDao.selectProjectInfo(projectId);
    }

    @Override
    public IPage<ProjectEventInfo> queryProjectEvents(ProjectEventVO vo) {
        return this.eventInfoDao.selectProjects(vo.getPageNo(), vo.getPageSize(), vo.getCompanyId(), vo.getProjectId(), vo.getStatus(), vo.getBeginDate(), vo.getEndDate());
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
}
