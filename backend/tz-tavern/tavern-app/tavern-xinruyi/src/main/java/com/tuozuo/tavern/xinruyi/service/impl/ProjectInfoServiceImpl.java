package com.tuozuo.tavern.xinruyi.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tuozuo.tavern.xinruyi.convert.ModelConverterFactory;
import com.tuozuo.tavern.xinruyi.dao.ProjectInfoDao;
import com.tuozuo.tavern.xinruyi.dao.ProjectStaffInfoDao;
import com.tuozuo.tavern.xinruyi.model.ProjectInfo;
import com.tuozuo.tavern.xinruyi.model.ProjectStaff;
import com.tuozuo.tavern.xinruyi.model.ProjectStaffInfo;
import com.tuozuo.tavern.xinruyi.service.ProjectInfoService;
import com.tuozuo.tavern.xinruyi.utils.DateUtils;
import com.tuozuo.tavern.xinruyi.utils.FileUtils;
import com.tuozuo.tavern.xinruyi.utils.ValidateUtils;
import com.tuozuo.tavern.xinruyi.vo.ProjectAddVO;
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
import java.util.Date;
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
    @Value("${xinruyi.company.file.path:/mnt/file/project/file/}")
    private String filePath;

    @Autowired
    private ProjectInfoDao projectInfoDao;
    @Autowired
    private ProjectStaffInfoDao projectStaffInfoDao;


    @Override
    public List<ProjectInfo> queryProjectInfo(String companyId, String projectName, int queryCnt, Boolean all) {
        if (all) {
            return this.projectInfoDao.selectAllProjectInfo(companyId, projectName);
        } else {
            return this.projectInfoDao.selectProjectInfo(companyId, projectName, queryCnt);
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
    public void delProjectStaff(String projectId, String staffId) throws Exception {
        ProjectStaff projectStaff = new ProjectStaff();
        projectStaff.setProjectId(projectId);
        projectStaff.setStaffId(staffId);
        projectStaff.setStatus("1");
        this.projectStaffInfoDao.delProjectStaff(projectStaff);
        //TODO 增加裁员变动事件

    }

    @Override
    public IPage<ProjectInfo> queryProjectInfo(ProjectListVo vo) {
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
                vo.getProjectId(),
                vo.getIndustryType(),
                downLimitBudget,
                upperLimitBudget,
                vo.getRequirementStatus());
    }

    @Override
    public void addProjectInfo(ProjectAddVO vo, String companyId) throws Exception {
        ProjectInfo projectInfo = ModelConverterFactory.addVoToProjectInfo(vo, companyId);
        this.setProjectInfoFiles(vo.getProjectFile(), projectInfo);
        this.projectInfoDao.insertProject(projectInfo);
        //TODO 项目发布事件

    }

    @Override
    public void modifyProjectInfo(ProjectModifyVO vo, String companyId) throws Exception {
        String today = DateUtils.formatDate(LocalDate.now(), DateUtils.SIMPLE_8_FORMATTER);
        if (vo.getReleaseDate().compareTo(today) > 0) {
            throw new Exception("项目已发布，修改失败");
        }
        ProjectInfo projectInfo = ModelConverterFactory.modifyVoToProjectInfo(vo, companyId);
        this.setProjectInfoFiles(vo.getProjectFile(), projectInfo);
        this.projectInfoDao.modifyProject(projectInfo);

    }

    @Override
    public void modifyProjectStatus(String status, String project) {
        ProjectInfo projectInfo = new ProjectInfo();
        projectInfo.setProjectId(project);
        projectInfo.setStatus(status);
        this.projectInfoDao.modifyProject(projectInfo);
    }

    @Override
    public ProjectInfo queryProjectDetail(String projectId) {
        return this.projectInfoDao.selectProjectInfo(projectId);
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
