package com.bankmonitoring.tm_alerting.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.Instant;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor @Builder
@Document(collection = "transactions")
public class Transaction {

    @Id
    private String id;

    // tài khoản nguồn
    private String sourceAccount;

    // tài khoản đích
    private String destinationAccount;

    // số tiền giao dịch
    private BigDecimal amount;

    // loại tiền tệ, ví dụ "VND" / "USD"
    private String currency;

    // timestamp khi giao dịch xảy ra (UTC)
    private Instant timestamp;
}