package com.tuozuo.tavern.shuiruyi.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.tuozuo.tavern.common.protocol.TavernResponse;
import com.tuozuo.tavern.shuiruyi.convert.BusinessConverter;
import com.tuozuo.tavern.shuiruyi.dao.CustomInfoDao;
import com.tuozuo.tavern.shuiruyi.dao.CustomTradeFlowDao;
import com.tuozuo.tavern.shuiruyi.dict.TradeEvent;
import com.tuozuo.tavern.shuiruyi.model.CustomDetailInfo;
import com.tuozuo.tavern.shuiruyi.model.CustomInfo;
import com.tuozuo.tavern.shuiruyi.model.CustomTradeFlow;
import com.tuozuo.tavern.shuiruyi.service.CustomInfoService;
import com.tuozuo.tavern.shuiruyi.utils.FileUtils;
import com.tuozuo.tavern.shuiruyi.utils.UUIDUtil;
import com.tuozuo.tavern.shuiruyi.vo.CustomAddInfoVO;
import com.tuozuo.tavern.shuiruyi.vo.CustomInfoVO;

import com.tuuozuo.tavern.authority.spi.AuthorityService;
import com.tuuozuo.tavern.authority.spi.vo.UserVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/7/18 <br>
 */
@Service
public class CustomInfoServiceImpl implements CustomInfoService {

    @Value("${shuiruyi.file.url.path:http://119.3.19.171/shuiruyi/file/trade/file/}")
    private String tradeFileUrlPath;
    @Value("${shuiruyi.custom.trade.file.path:/mnt/file/trade/file/}")
    private String tradeFilePath;
    @Autowired
    private AuthorityService authorityService;

    @Autowired
    private CustomInfoDao customInfoDao;
    @Autowired
    private CustomTradeFlowDao customTradeFlowDao;

    @Override
    public CustomDetailInfo queryCustomInfo(String customId) {
        return this.customInfoDao.selectCustomInfo(customId);
    }

    @Override
    public IPage<CustomTradeFlow> queryCustomTradeFlowList(String customId, int pageNo, int pageSize) {
        return this.customInfoDao.selectCustomTradeFlowList(customId, pageNo, pageSize);
    }

    @Override
    public List<CustomInfo> fuzzyQueryCustomInfo(String customName, int queryCnt) {
        return this.customInfoDao.fuzzyQueryCustomInfo(customName, queryCnt);
    }

    @Override
    public void addCustomInfo(CustomAddInfoVO vo) throws Exception {
        UserVO userVO = BusinessConverter.userToVO(vo);
        TavernResponse response = this.authorityService.createUser(userVO);
        if (response.getCode() != 0) {
            throw new Exception("客户创建失败");
        }
        CustomInfo customInfo = new CustomInfo();
        customInfo.setCustomId(vo.getCustomId());
        setCustomInfo(vo, customInfo);
        this.customInfoDao.insert(customInfo);

    }

    @Override
    public void modifyCustomInfo(CustomInfoVO vo, String customId) {
        CustomInfo customInfo = new CustomInfo();
        customInfo.setCustomId(customId);
        setCustomInfo(vo, customInfo);
        this.customInfoDao.update(customInfo);
    }

    @Override
    public IPage<CustomInfo> queryCustomInfoList(String customName, String hasPaid, int pageNo, int pageSize) {
        return this.customInfoDao.selectCustomInfoList(customName, hasPaid, pageNo, pageSize);
    }

    @Transactional
    @Override
    public String investAndPayment(String customId, String customType, String event, double amount, MultipartFile tradeSnapshot) throws Exception {
        //1、扣款或者充值，余额变动
        //2、流水记录,凭证上传
        CustomInfo customInfo = this.customInfoDao.selectCustomInfo(customId);
        String customInfoString = JSON.toJSONString(customInfo);
        double balance = customInfo.getBalance() == null ? 0 : customInfo.getBalance().doubleValue();
        double newBalance = this.calBalance(event, balance, amount);
        customInfo.setBalance(new BigDecimal(newBalance));
        this.customInfoDao.update(customInfo);

        CustomTradeFlow customTradeFlow = new CustomTradeFlow();
        String tradeFlowId = UUIDUtil.randomUUID32();
        if (tradeSnapshot != null) {
            //路径为：path + customId + tradeFlowId
            String urlLocation = this.fileStore(customId, tradeFlowId, tradeSnapshot);
            customTradeFlow.setTradeSnapshot(urlLocation);
        }
        customTradeFlow.setTradeFlowId(tradeFlowId);
        customTradeFlow.setCustomId(customId);
        customTradeFlow.setTradeDate(new Date());
        customTradeFlow.setAmount(new BigDecimal(amount));
        customTradeFlow.setTradeType(TradeEvent.valueOf(event).getEvent());
        customTradeFlow.setBalance(new BigDecimal(newBalance));
        customTradeFlow.setEvent(event);
        customTradeFlow.setRemark(customInfoString);
        this.customTradeFlowDao.insert(customTradeFlow);
        return tradeFlowId;
    }

    @Override
    public void countCustomStatistic() {
        this.customInfoDao.countCustomStatistic();
    }

    private void setCustomInfo(CustomInfoVO vo, CustomInfo customInfo) {
        customInfo.setCustomName(vo.getCustomName());
        customInfo.setCustomPswd(vo.getCustomPswd());
        customInfo.setCustomContact(vo.getCustomContact());
        customInfo.setProvince(vo.getProvince());
        customInfo.setCustomType(vo.getCustomType());
        customInfo.setHasPaid(vo.getHasPaid());
        customInfo.setUpdateDate(LocalDateTime.now());
        customInfo.setBalance(BigDecimal.ZERO);
    }

    private String fileStore(String customId, String tradeFlowId, MultipartFile tradeSnapshot) throws Exception {
        String pathLocation = StringUtils.join(tradeFilePath, customId,
                "/", tradeFlowId);
        FileUtils.multiPartFileWriter(tradeSnapshot, pathLocation);
        return StringUtils.join(tradeFileUrlPath, customId,
                "/", tradeFlowId, "/", tradeSnapshot.getOriginalFilename());
    }

    private double calBalance(String event, double balance, double amount) throws Exception {

        double newBalance;
        if (event.equals(TradeEvent.CUT_PAYMENT.name())) {
            //1、余额不足
            if (balance < amount) {
                throw new Exception("余额不足");
            } else {
                newBalance = balance - amount;
            }
        } else {
            newBalance = balance + amount;
        }
        return newBalance;
    }
}
