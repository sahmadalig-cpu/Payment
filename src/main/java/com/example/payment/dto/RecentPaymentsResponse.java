package com.example.payment.dto;

import java.util.List;

public class RecentPaymentsResponse {

    private List<PaymentSummary> payments;

    public RecentPaymentsResponse() {
    }

    public RecentPaymentsResponse(List<PaymentSummary> payments) {
        this.payments = payments;
    }

    public List<PaymentSummary> getPayments() {
        return payments;
    }

    public void setPayments(List<PaymentSummary> payments) {
        this.payments = payments;
    }
}
