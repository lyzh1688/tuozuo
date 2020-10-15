package com.tuozuo.tavern.xinruyi.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tuozuo.tavern.common.protocol.UserTypeDict;
import com.tuozuo.tavern.xinruyi.dao.EventInfoDao;
import com.tuozuo.tavern.xinruyi.dao.WorkerInfoDao;
import com.tuozuo.tavern.xinruyi.dict.EventType;
import com.tuozuo.tavern.xinruyi.dto.EventInfoDTO;
import com.tuozuo.tavern.xinruyi.model.EventTodoList;
import com.tuozuo.tavern.xinruyi.model.ProjectInfo;
import com.tuozuo.tavern.xinruyi.model.WorkerInfo;
import com.tuozuo.tavern.xinruyi.model.WorkerSummaryInfo;
import com.tuozuo.tavern.xinruyi.service.WorkerInfoService;
import com.tuozuo.tavern.xinruyi.utils.FileUtils;
import com.tuozuo.tavern.xinruyi.utils.UUIDUtil;
import com.tuozuo.tavern.xinruyi.vo.WorkerAuthVO;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/10/14 <br>
 */
@Service
public class WorkerInfoServiceImpl implements WorkerInfoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(WorkerInfoServiceImpl.class);

    @Value("${xinruyi.file.url.path:http://119.3.19.171/xinruyi/file/worker/auth/}")
    private String fileUrlPath;
    @Value("${xinruyi.company.file.path:/mnt/file/xinruyi/worker/auth/}")
    private String filePath;

    @Autowired
    private WorkerInfoDao workerInfoDao;
    @Autowired
    private EventInfoDao eventInfoDao;

    @Override
    public WorkerSummaryInfo queryWorkerSumInfo(String registerId) {
        return this.workerInfoDao.selectWorkerSumInfo(registerId);
    }

    @Transactional
    @Override
    public void addWorker(WorkerAuthVO vo) throws Exception {

        //1、新增用户
        WorkerInfo workerInfo = new WorkerInfo();
        workerInfo.setRegisterId(vo.getRegisterId());
        workerInfo.setIdNumber(vo.getIdNo());
        workerInfo.setContact(vo.getContact());
        workerInfo.setName(workerInfo.getName());
        this.setWorkerInfoFiles(vo.getVideo(),vo.getIdPicUp(),vo.getIdPicDown(),workerInfo);
        this.workerInfoDao.insert(workerInfo);


        //2、发布审核申请
        EventTodoList eventTodoList = new EventTodoList();
        JSONObject eventSnapshot = new JSONObject();
        eventSnapshot.put("registerId", vo.getRegisterId());

        eventTodoList.setSnapshot(JSON.toJSONString(eventSnapshot));
        eventTodoList.setApplicant(vo.getName());
        eventTodoList.setEventId(UUIDUtil.randomUUID32());
        eventTodoList.setEventType(EventType.STAFF_AUTH.getStatus());
        eventTodoList.setRole(UserTypeDict.staff);
        eventTodoList.setEventOwnerName(vo.getName());
        eventTodoList.setEventDate(LocalDateTime.now());
        eventTodoList.setRegisterId(vo.getRegisterId());
        eventTodoList.setEventOwnerId(vo.getIdNo());
        this.eventInfoDao.insertEventTodo(eventTodoList);

    }


    private void setWorkerInfoFiles(MultipartFile video,
            MultipartFile idPicUp,
            MultipartFile idPicBack,
            WorkerInfo workerInfo) throws Exception {
        if (video != null) {
            String videoUrl = this.storeWorkerFile(workerInfo.getIdNumber(), video);
            LOGGER.info("videoUrl: {}", videoUrl);
            workerInfo.setVideo(videoUrl);
        }
        if (idPicUp != null) {
            String idPicUpUrl = this.storeWorkerFile(workerInfo.getIdNumber(), idPicUp);
            LOGGER.info("idPicUpUrl: {}", idPicUpUrl);
            workerInfo.setIdPicUp(idPicUpUrl);
        }
        if (idPicBack != null) {
            String idPicBackUrl = this.storeWorkerFile(workerInfo.getIdNumber(), idPicBack);
            LOGGER.info("idPicBackUrl: {}", idPicBackUrl);
            workerInfo.setIdPicBack(idPicBackUrl);
        }

    }

    //path + idNo + file
    private String storeWorkerFile(String idNo, MultipartFile file) throws Exception {

        String pathLocation = StringUtils.join(filePath, idNo,
                "/");
        String fileName = FileUtils.multiPartFileWriter(file, pathLocation);
        return StringUtils.join(fileUrlPath, idNo,
                "/", fileName);

    }
}
