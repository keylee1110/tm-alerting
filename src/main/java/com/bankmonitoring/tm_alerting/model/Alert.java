package com.bankmonitoring.tm_alerting.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor @Builder
@Document(collection = "alerts")
public class Alert {

    @Id
    private String id;

    // id giao dịch gây cảnh báo
    private String transactionId;

    // loại cảnh báo (ví dụ: HIGH_VALUE, RAPID_FREQUENCY)
    private String type;

    // mô tả ngắn gọn để UI/analyst đọc hiểu
    private String message;

    // trạng thái xử lý
    private AlertStatus status;

    // khi alert được tạo
    private Instant createdAt;
}