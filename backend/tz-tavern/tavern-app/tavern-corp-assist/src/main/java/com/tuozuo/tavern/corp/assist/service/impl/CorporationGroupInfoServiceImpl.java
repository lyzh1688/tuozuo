package com.tuozuo.tavern.corp.assist.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.tuozuo.tavern.corp.assist.convert.ModelMapConverterFactory;
import com.tuozuo.tavern.corp.assist.dao.CorporationClientGroupRelDao;
import com.tuozuo.tavern.corp.assist.dao.CorporationClientInfoDao;
import com.tuozuo.tavern.corp.assist.dao.CorporationGroupInfoDao;
import com.tuozuo.tavern.corp.assist.dict.ValidType;
import com.tuozuo.tavern.corp.assist.dto.CorporationGroupClientDTO;
import com.tuozuo.tavern.corp.assist.dto.CorporationGroupMember;
import com.tuozuo.tavern.corp.assist.facade.corpwechat.model.*;
import com.tuozuo.tavern.corp.assist.facade.corpwechat.service.WechatGroupChatService;
import com.tuozuo.tavern.corp.assist.model.CorporationClientGroupRel;
import com.tuozuo.tavern.corp.assist.model.CorporationClientInfo;
import com.tuozuo.tavern.corp.assist.model.CorporationGroupClientInfo;
import com.tuozuo.tavern.corp.assist.model.CorporationGroupInfo;
import com.tuozuo.tavern.corp.assist.service.CorporationGroupInfoService;
import com.tuozuo.tavern.corp.assist.utils.DateUtils;
import com.tuozuo.tavern.corp.assist.utils.UUIDUtil;
import com.tuozuo.tavern.corp.assist.vo.CorporationGroupInfoVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2021/1/3 <br>
 */
@Service
public class CorporationGroupInfoServiceImpl implements CorporationGroupInfoService {

    @Autowired
    private CorporationGroupInfoDao corporationGroupInfoDao;
    @Autowired
    private CorporationClientGroupRelDao corporationClientGroupRelDao;
    @Autowired
    private ModelMapConverterFactory converterFactory;
    @Autowired
    private WechatGroupChatService wechatGroupChatService;
    @Autowired
    private CorporationClientInfoDao corporationClientInfoDao;

    @Override
    public boolean addGroup(CorporationGroupInfoVO groupInfoVO) {
        CorporationGroupInfo corporationGroupInfo = converterFactory.dtoToCorporationGroupInfo(groupInfoVO);
        corporationGroupInfo.setGroupId(UUIDUtil.randomUUID32());
        corporationGroupInfo.setCreateTime(LocalDateTime.now());
        return this.corporationGroupInfoDao.insertGroup(corporationGroupInfo);
    }

    @Override
    public boolean delGroup(String groupId) throws Exception {
        boolean isValidGroup = this.corporationClientGroupRelDao.isValidGroup(groupId);
        if (isValidGroup) {
            throw new Exception("该群尚有群成员,无法删除");
        }
        CorporationGroupInfo corporationGroupInfo = CorporationGroupInfo.create(groupId, ValidType.INVALID.getType());
        return this.corporationGroupInfoDao.delGroup(corporationGroupInfo);
    }

    @Override
    public boolean modifyGroup(CorporationGroupInfoVO groupInfoVO) {
        CorporationGroupInfo corporationGroupInfo = converterFactory.dtoToCorporationGroupInfo(groupInfoVO);
        return this.corporationGroupInfoDao.updateGroup(corporationGroupInfo);
    }

    @Override
    public IPage<CorporationGroupClientInfo> queryGroups(String tagName, String groupName, int pageNo, int pageSize) {
        Page<CorporationGroupClientInfo> page = new Page<>(pageNo, pageSize);
        return this.corporationGroupInfoDao.selectGroup(page, tagName, groupName);
    }

    @Override
    public CorporationGroupClientInfo queryGroupDetail(String groupId) {
        return this.corporationGroupInfoDao.selectGroupDetail(groupId);
    }

    @Transactional
    @Override
    public boolean bindGroupClientRel(String groupId, List<String> clientId) {
        List<CorporationClientGroupRel> relList = clientId.stream()
                .map(c -> CorporationClientGroupRel.create(c, groupId))
                .collect(Collectors.toList());
        this.corporationClientGroupRelDao.delByGroupId(groupId);
        return this.corporationClientGroupRelDao.insertBatch(relList);
    }

    @Override
    public List<CorporationGroupClientInfo> queryGroupsFromApp(String tagName, String groupName, String groupId, String createTime) {
        if (StringUtils.isEmpty(createTime)) {
            createTime = DateUtils.formatDateTime(LocalDateTime.now().plusMonths(1), DateUtils.DEFAULT_DATETIME_FORMATTER);
        }
        return this.corporationGroupInfoDao.selectGroupsFromApp(tagName, groupName, groupId, createTime);
    }

    @Override
    public CorporationGroupClientDTO queryCorporationGroupClient(String chatId) throws Exception {
        //1、获取accessToken
        //2、获取群信息
        //3、获取群客户列表
        //4、获取绑定信息
        //5、拼接返回


        AccessToken accessToken = this.wechatGroupChatService.getAccessToken();
        if (Objects.isNull(accessToken)) {
            throw new Exception("access token 失效");
        }
        GroupChatDetail groupChatDetail = this.wechatGroupChatService.getGroupChat(accessToken.getAccessToken(), chatId);
        if (Objects.isNull(groupChatDetail)) {
            throw new Exception("企业微信群获取失败");
        }


        //拉出所有的库中所有groupClient
        CorporationGroupClientInfo corporationGroupClientInfo = this.corporationGroupInfoDao.selectGroup(chatId);

        //1、group:
        //则返回微信用户，用户和绑定后端关系

        //创建group维度信息
        CorporationGroupClientDTO corporationGroupClientDTO = CorporationGroupClientDTO.construct(groupChatDetail.getGroupChat().getChatId(), groupChatDetail.getGroupChat().getName());
        this.createBasicGroupInfo(corporationGroupClientDTO, groupChatDetail.getGroupChat().getChatId(), groupChatDetail.getGroupChat().getName());
        if (corporationGroupClientInfo == null) {
            corporationGroupClientDTO.setStatus("2");
        } else {
            corporationGroupClientDTO.getGroupChat().setChatIdBackend(corporationGroupClientInfo.getGroupId());
            corporationGroupClientDTO.setStatus("1");
        }


        //群成员维度信息
        Map<String, CorporationClientInfo> clientInfoMap = Maps.newHashMap();
        List<CorporationClientInfo> unBindClients = Lists.newArrayList();


        if (Objects.nonNull(corporationGroupClientInfo)) {
            clientInfoMap = corporationGroupClientInfo.getClients()
                    .stream()
                    .filter(c -> c.getUserId() != null)
                    .collect(Collectors.toMap(CorporationClientInfo::getUserId, v -> v));
            unBindClients = corporationGroupClientInfo.getClients()
                    .stream()
                    .filter(c -> c.getUserId() == null)
                    .collect(Collectors.toList());
        }


        List<CorporationGroupMember> corporationGroupMembers = Lists.newArrayList();
        List<MemberList> memberLists = groupChatDetail.getGroupChat().getMemberList();

        //有关联关系用户
        Map<String, CorporationClientInfo> finalClientInfoMap = clientInfoMap;
        memberLists.parallelStream()
                .forEach(m -> {
                    ClientInfo clientInfo = this.wechatGroupChatService.getClientInfo(accessToken.getAccessToken(), m.getUserId());
                    //创建group member
                    CorporationGroupMember groupMember = new CorporationGroupMember();
                    groupMember.setUserId(m.getUserId());
                    if (Objects.nonNull(clientInfo)) {
                        groupMember = this.converterFactory.clientInfoToGroupMember(clientInfo);
                        if (finalClientInfoMap.containsKey(clientInfo.getExternalUserId())) {
                            CorporationClientInfo corporationClientInfo = finalClientInfoMap.get(clientInfo.getExternalUserId());
                            groupMember.setUserIdBackend(corporationClientInfo.getClientId());
                            groupMember.setStatus("3");
                        } else {
                            groupMember.setStatus("2");
                        }
                    } else {
                        groupMember.setStatus("2");
                    }
                    corporationGroupMembers.add(groupMember);
                });

        //处理未关联
        for (CorporationClientInfo c : unBindClients) {
            CorporationGroupMember groupMember = new CorporationGroupMember();
            groupMember.setUserIdBackend(c.getClientId());
            groupMember.setStatus("1");
            corporationGroupMembers.add(groupMember);
        }

        corporationGroupClientDTO.getGroupChat().setMemberList(corporationGroupMembers);


        return corporationGroupClientDTO;
    }

    private void createBasicGroupInfo(CorporationGroupClientDTO corporationGroupClientDTO, String chatId, String chatName) {
        corporationGroupClientDTO.getGroupChat().setChatId(chatId);
        corporationGroupClientDTO.getGroupChat().setName(chatName);
    }

}
