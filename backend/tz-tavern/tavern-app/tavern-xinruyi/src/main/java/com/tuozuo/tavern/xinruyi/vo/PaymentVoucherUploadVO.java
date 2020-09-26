package com.tuozuo.tavern.xinruyi.vo;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/9/19 <br>
 */
public class PaymentVoucherUploadVO {
    @NotNull(message = "projectId is not null")
    private String projectId;
    @NotNull(message = "amount is not null")
    private BigDecimal amount;
    @NotNull(message = "month is not null")
    private String month;
//    @NotNull(message = "payDate is not null")
    private String payDate;
    private MultipartFile voucher;
    private String paymentId;

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getPayDate() {
        return payDate;
    }

    public void setPayDate(String payDate) {
        this.payDate = payDate;
    }

    public MultipartFile getVoucher() {
        return voucher;
    }

    public void setVoucher(MultipartFile voucher) {
        this.voucher = voucher;
    }
}
