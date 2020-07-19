package com.tuozuo.tavern.shuiruyi.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tuozuo.tavern.shuiruyi.dao.CustomInfoDao;
import com.tuozuo.tavern.shuiruyi.model.CustomDetailInfo;
import com.tuozuo.tavern.shuiruyi.model.CustomInfo;
import com.tuozuo.tavern.shuiruyi.model.CustomTradeFlow;
import com.tuozuo.tavern.shuiruyi.service.CustomInfoService;
import com.tuozuo.tavern.shuiruyi.utils.UUIDUtil;
import com.tuozuo.tavern.shuiruyi.vo.CustomInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/7/18 <br>
 */
@Service
public class CustomInfoServiceImpl implements CustomInfoService {
    @Value("${custom.trade.file.url.path:http://119.3.19.171/custom/trade/file/}")
    private String tradeFileUrlPath;
    @Value("${custom.trade.file.path:/mnt/file/trade/file/}")
    private String tradeFilePath;

    @Autowired
    private CustomInfoDao customInfoDao;

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
    public void addCustomInfo(CustomInfoVO vo) {

        CustomInfo customInfo = new CustomInfo();
        customInfo.setCustomId(UUIDUtil.randomUUID32());
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
    public void investAndPayment(String customId, String customType, String event, String amount, MultipartFile tradeSnapshot) {
        //1、扣款或者充值，余额变动
        //2、流水记录,凭证上传




    }

    private void setCustomInfo(CustomInfoVO vo, CustomInfo customInfo) {
        customInfo.setCustomPswd(vo.getCustomPswd());
        customInfo.setCustomContact(vo.getCustomContact());
        customInfo.setProvince(vo.getProvince());
        customInfo.setCustomType(vo.getCustomType());
        customInfo.setHasPaid(vo.getHasPaid());
    }
}
