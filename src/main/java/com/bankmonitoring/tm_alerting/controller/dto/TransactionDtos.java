package com.bankmonitoring.tm_alerting.controller.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public class TransactionDtos {

    @Getter @Setter @AllArgsConstructor @NoArgsConstructor
    public static class TransactionRequest {
        private String sourceAccount;
        private String destinationAccount;
        private BigDecimal amount;
        private String currency;        // "VND"
        private Instant timestamp;      // nếu client không gửi, ta sẽ set now
    }

    @Getter @Setter @AllArgsConstructor @NoArgsConstructor @Builder
    public static class TransactionResponse {
        private String id;
        private List<String> alertIds; // các alert phát sinh từ giao dịch này
    }
}