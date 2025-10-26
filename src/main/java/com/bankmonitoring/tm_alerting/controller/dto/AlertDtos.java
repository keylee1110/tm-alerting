package com.bankmonitoring.tm_alerting.controller.dto;

import com.bankmonitoring.tm_alerting.model.AlertStatus;
import lombok.*;

import java.time.Instant;

public class AlertDtos {

    @Getter @Setter @AllArgsConstructor @NoArgsConstructor @Builder
    public static class AlertView {
        private String id;
        private String transactionId;
        private String type;
        private String message;
        private AlertStatus status;
        private Instant createdAt;
    }

    @Getter @Setter @AllArgsConstructor @NoArgsConstructor
    public static class ResolveRequest {
        private String status; // "RESOLVED"
    }
}
