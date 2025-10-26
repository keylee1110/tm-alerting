package com.bankmonitoring.tm_alerting.service;

import com.bankmonitoring.tm_alerting.controller.dto.TransactionDtos.*;
import com.bankmonitoring.tm_alerting.event.DomainEventPublisher;
import com.bankmonitoring.tm_alerting.model.Transaction;
import com.bankmonitoring.tm_alerting.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionRuleService transactionRuleService;
    private final DomainEventPublisher domainEventPublisher;

    public TransactionResponse createTransaction(TransactionRequest req) {

        Instant ts = (req.getTimestamp() == null) ? Instant.now() : req.getTimestamp();

        Transaction txn = Transaction.builder()
                .sourceAccount(req.getSourceAccount())
                .destinationAccount(req.getDestinationAccount())
                .amount(req.getAmount())
                .currency(req.getCurrency())
                .timestamp(ts)
                .build();

        // 1. save
        txn = transactionRepository.save(txn);

        // 2. publish event (async-ish processing in listener)
        domainEventPublisher.publishTransactionCreated(txn);

        // 3. vẫn chạy rule sync để FE nhận được alertIds ngay trong response trả về
        var alerts = transactionRuleService.evaluateAndCreateAlerts(txn);

        return TransactionResponse.builder()
                .id(txn.getId())
                .alertIds(alerts.stream().map(a -> a.getId()).toList())
                .build();
    }
}
