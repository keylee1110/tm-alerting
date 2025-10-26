package com.bankmonitoring.tm_alerting.service;

import com.bankmonitoring.tm_alerting.controller.dto.TransactionDtos.*;
import com.bankmonitoring.tm_alerting.model.Transaction;
import com.bankmonitoring.tm_alerting.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionRuleService transactionRuleService;

    public TransactionResponse createTransaction(TransactionRequest req) {

        // fallback timestamp nếu client không gửi
        Instant ts = (req.getTimestamp() == null) ? Instant.now() : req.getTimestamp();

        Transaction txn = Transaction.builder()
                .sourceAccount(req.getSourceAccount())
                .destinationAccount(req.getDestinationAccount())
                .amount(req.getAmount())
                .currency(req.getCurrency())
                .timestamp(ts)
                .build();

        // 1. Lưu transaction
        txn = transactionRepository.save(txn);

        // 2. Chạy rule => trả về list alert
        var alerts = transactionRuleService.evaluateAndCreateAlerts(txn);

        // 3. Trả response
        return TransactionResponse.builder()
                .id(txn.getId())
                .alertIds(alerts.stream().map(a -> a.getId()).toList())
                .build();
    }
}
