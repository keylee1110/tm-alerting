package com.bankmonitoring.tm_alerting.service;

import com.bankmonitoring.tm_alerting.model.*;
import com.bankmonitoring.tm_alerting.repository.AlertRepository;
import com.bankmonitoring.tm_alerting.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionRuleService {

    private final TransactionRepository transactionRepository;
    private final AlertRepository alertRepository;

    // Ngưỡng mẫu: giao dịch > 100_000_000 VND
    private static final BigDecimal HIGH_VALUE_THRESHOLD = new BigDecimal("100000000");

    // Ngưỡng tần suất: >5 giao dịch trong vòng 60 giây
    private static final int RAPID_TXN_LIMIT = 5;
    private static final Duration RAPID_WINDOW = Duration.ofSeconds(60);

    public List<Alert> evaluateAndCreateAlerts(Transaction txnJustSaved) {
        List<Alert> created = new ArrayList<>();

        // Rule 1: High value
        if ("VND".equalsIgnoreCase(txnJustSaved.getCurrency())
                && txnJustSaved.getAmount().compareTo(HIGH_VALUE_THRESHOLD) > 0) {

            Alert alert = Alert.builder()
                    .transactionId(txnJustSaved.getId())
                    .type("HIGH_VALUE")
                    .message("Transaction amount " + txnJustSaved.getAmount() +
                            " " + txnJustSaved.getCurrency() + " exceeds threshold")
                    .status(AlertStatus.PENDING)
                    .createdAt(Instant.now())
                    .build();

            created.add(alertRepository.save(alert));
        }

        // Rule 2: Rapid frequency
        Instant end   = txnJustSaved.getTimestamp();
        Instant start = end.minus(RAPID_WINDOW);

        var recentFromSameSource = transactionRepository
                .findBySourceAccountAndTimestampBetween(
                        txnJustSaved.getSourceAccount(), start, end
                );

        if (recentFromSameSource.size() > RAPID_TXN_LIMIT) {
            Alert alert = Alert.builder()
                    .transactionId(txnJustSaved.getId())
                    .type("RAPID_FREQUENCY")
                    .message("More than " + RAPID_TXN_LIMIT +
                            " txns in " + RAPID_WINDOW.getSeconds() + "s " +
                            "from account " + txnJustSaved.getSourceAccount())
                    .status(AlertStatus.PENDING)
                    .createdAt(Instant.now())
                    .build();

            created.add(alertRepository.save(alert));
        }

        return created;
    }
}