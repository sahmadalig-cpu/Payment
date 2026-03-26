package com.example.payment.controller;

import com.example.payment.dto.PaymentRequest;
import com.example.payment.dto.PaymentResponse;
import com.example.payment.dto.RecentPaymentsResponse;
import com.example.payment.service.PaymentService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payments")
@Validated
public class PaymentController {

    private static final int DEFAULT_LIMIT = 4;
    private static final int MAX_LIMIT = 10;

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/authorize")
    public ResponseEntity<PaymentResponse> authorize(@Valid @RequestBody PaymentRequest request) {
        return ResponseEntity.ok(paymentService.authorizePayment(request));
    }

    @GetMapping("/recent")
    public ResponseEntity<RecentPaymentsResponse> getRecentPayments(
            @RequestParam @NotBlank String customerId,
            @RequestParam(defaultValue = "4") @Min(1) @Max(MAX_LIMIT) int limit) {

        int finalLimit = Math.min(Math.max(limit, DEFAULT_LIMIT), MAX_LIMIT);
        return ResponseEntity.ok(paymentService.getRecentPayments(customerId, finalLimit));
    }
}
