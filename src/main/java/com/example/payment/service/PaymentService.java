package com.example.payment.service;

import com.example.payment.client.GatewayClient;
import com.example.payment.dto.PaymentRequest;
import com.example.payment.dto.PaymentResponse;
import com.example.payment.dto.RecentPaymentsResponse;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class PaymentService {

    private final GatewayClient gatewayClient;

    public PaymentService(GatewayClient gatewayClient) {
        this.gatewayClient = gatewayClient;
    }

    public PaymentResponse authorizePayment(PaymentRequest request) {
        validatePaymentRequest(request);
        return gatewayClient.callAuthorizeApi(request);
    }

    public RecentPaymentsResponse getRecentPayments(String customerId, int limit) {
        if (!StringUtils.hasText(customerId)) {
            throw new IllegalArgumentException("customerId must not be blank");
        }
        if (limit <= 0) {
            throw new IllegalArgumentException("limit must be greater than zero");
        }
        return gatewayClient.callRecentPaymentsApi(customerId, limit);
    }

    private void validatePaymentRequest(PaymentRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("payment request must not be null");
        }
        if (!StringUtils.hasText(request.getCustomerId())) {
            throw new IllegalArgumentException("customerId must not be blank");
        }
        if (request.getAmount() == null) {
            throw new IllegalArgumentException("amount must not be null");
        }
    }
}
