package com.tuozuo.tavern.shuiruyi.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tuozuo.tavern.shuiruyi.model.CustomDetailInfo;
import com.tuozuo.tavern.shuiruyi.model.CustomInfo;
import com.tuozuo.tavern.shuiruyi.model.CustomTradeFlow;
import com.tuozuo.tavern.shuiruyi.vo.CustomAddInfoVO;
import com.tuozuo.tavern.shuiruyi.vo.CustomInfoVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/7/18 <br>
 */
public interface CustomInfoService {

    CustomDetailInfo queryCustomInfo(String customId);

    IPage<CustomTradeFlow> queryCustomTradeFlowList(String customId, int pageNo, int pageSize);

    List<CustomInfo> fuzzyQueryCustomInfo(String customName, int queryCnt);

    void addCustomInfo(CustomAddInfoVO vo) throws Exception;

    void modifyCustomInfo(CustomInfoVO vo, String customId);

    IPage<CustomInfo> queryCustomInfoList(String customName, String hasPaid, int pageNo, int pageSize);

    String investAndPayment(String customId,
                            String customType,
                            String event,
                            double amount,
                            MultipartFile tradeFile,
                            String remark) throws Exception;

    void countCustomStatistic();

}
