package com.example.payment.client;

import com.example.payment.dto.PaymentRequest;
import com.example.payment.dto.PaymentResponse;
import com.example.payment.dto.RecentPaymentsResponse;
import com.example.payment.exception.PaymentGatewayException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Component
public class GatewayClient {

    private static final String AUTHORIZE_PATH = "/v1/payments/authorize";
    private static final String RECENT_PAYMENTS_PATH = "/v1/payments/recent";

    private final RestTemplate restTemplate;

    @Value("${gateway.base-url}")
    private String gatewayBaseUrl;

    public GatewayClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public PaymentResponse callAuthorizeApi(PaymentRequest request) {
        validateBaseUrl();

        URI uri = UriComponentsBuilder
                .fromHttpUrl(gatewayBaseUrl)
                .path(AUTHORIZE_PATH)
                .build()
                .toUri();

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<PaymentRequest> entity = new HttpEntity<>(request, headers);

        try {
            ResponseEntity<PaymentResponse> response = restTemplate.exchange(
                    uri,
                    HttpMethod.POST,
                    entity,
                    PaymentResponse.class
            );
            return requireBody(response, "authorize payment");
        } catch (RestClientException ex) {
            throw new PaymentGatewayException("Failed to authorize payment", ex);
        }
    }

    public RecentPaymentsResponse callRecentPaymentsApi(String customerId, int limit) {
        validateBaseUrl();

        URI uri = UriComponentsBuilder
                .fromHttpUrl(gatewayBaseUrl)
                .path(RECENT_PAYMENTS_PATH)
                .queryParam("customerId", customerId)
                .queryParam("limit", limit)
                .build()
                .toUri();

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<RecentPaymentsResponse> response = restTemplate.exchange(
                    uri,
                    HttpMethod.GET,
                    entity,
                    RecentPaymentsResponse.class
            );
            return requireBody(response, "fetch recent payments");
        } catch (RestClientException ex) {
            throw new PaymentGatewayException("Failed to fetch recent payments", ex);
        }
    }

    private void validateBaseUrl() {
        if (!StringUtils.hasText(gatewayBaseUrl)) {
            throw new IllegalStateException("gateway.base-url must be configured");
        }
    }

    private <T> T requireBody(ResponseEntity<T> response, String operation) {
        T body = response.getBody();
        if (body == null) {
            throw new PaymentGatewayException("Partner API returned empty body for operation: " + operation);
        }
        return body;
    }
}
