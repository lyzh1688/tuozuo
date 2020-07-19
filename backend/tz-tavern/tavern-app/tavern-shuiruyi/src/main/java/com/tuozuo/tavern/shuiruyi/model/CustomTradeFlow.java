package com.tuozuo.tavern.shuiruyi.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.util.Date;

@TableName("custom_trade_flow")
public class CustomTradeFlow {
    @TableId
    private String tradeFlowId;

    private String customId;

    private Date tradeDate;

    private BigDecimal amount;

    private String tradeType;

    private BigDecimal balance;

    private String tradeSnapshot;

    private String event;

    private String remark;

    public String getTradeFlowId() {
        return tradeFlowId;
    }

    public void setTradeFlowId(String tradeFlowId) {
        this.tradeFlowId = tradeFlowId == null ? null : tradeFlowId.trim();
    }

    public String getCustomId() {
        return customId;
    }

    public void setCustomId(String customId) {
        this.customId = customId == null ? null : customId.trim();
    }

    public Date getTradeDate() {
        return tradeDate;
    }

    public void setTradeDate(Date tradeDate) {
        this.tradeDate = tradeDate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount == null ? null : amount;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType == null ? null : tradeType.trim();
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getTradeSnapshot() {
        return tradeSnapshot;
    }

    public void setTradeSnapshot(String tradeSnapshot) {
        this.tradeSnapshot = tradeSnapshot == null ? null : tradeSnapshot.trim();
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event == null ? null : event.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}