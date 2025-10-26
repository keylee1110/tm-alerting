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

        // 2. publish event for async processing in listener
        domainEventPublisher.publishTransactionCreated(txn);

        // 3. Return response immediately (alerts will be created async)
        return TransactionResponse.builder()
                .id(txn.getId())
                .alertIds(new java.util.ArrayList<>()) // Always return empty list
                .build();
    }
}
