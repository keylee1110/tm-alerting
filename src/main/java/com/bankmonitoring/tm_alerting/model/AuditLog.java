package com.bankmonitoring.tm_alerting.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor @Builder
@Document(collection = "audit_logs")
public class AuditLog {

    @Id
    private String id;

    // ví dụ RESOLVE_ALERT, CREATE_TRANSACTION, etc.
    private String action;

    // tham chiếu tới alert/giao dịch liên quan
    private String targetId;

    // ai thực hiện (email từ SecurityContext)
    private String actor;

    // mô tả để người xem dashboard hiểu
    private String message;

    // thời điểm tạo log
    private Instant createdAt;
}