package com.example.payment.dto;

import java.math.BigDecimal;

public class PaymentSummary {

    private String paymentId;
    private BigDecimal amount;
    private String status;
    private String paymentDate;

    public PaymentSummary() {
    }

    public PaymentSummary(String paymentId, BigDecimal amount, String status, String paymentDate) {
        this.paymentId = paymentId;
        this.amount = amount;
        this.status = status;
        this.paymentDate = paymentDate;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }
}
