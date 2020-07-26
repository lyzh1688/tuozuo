package com.tuozuo.tavern.shuiruyi.vo;

import javax.validation.constraints.NotNull;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/7/26 <br>
 */
public class InvoiceAuditVO {
    @NotNull(message = "invoiceStatus is not null")
    private String invoiceStatus;
    @NotNull(message = "deliveryId is not null")
    private String deliveryId;
    @NotNull(message = "remark is not null")
    private String remark;

    public String getInvoiceStatus() {
        return invoiceStatus;
    }

    public void setInvoiceStatus(String invoiceStatus) {
        this.invoiceStatus = invoiceStatus;
    }

    public String getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(String deliveryId) {
        this.deliveryId = deliveryId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
