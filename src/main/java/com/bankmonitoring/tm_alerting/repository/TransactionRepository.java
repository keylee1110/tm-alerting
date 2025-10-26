package com.bankmonitoring.tm_alerting.repository;

import com.bankmonitoring.tm_alerting.model.Transaction;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.Instant;
import java.util.List;

public interface TransactionRepository extends MongoRepository<Transaction, String> {

    // dùng cho rule "spam nhiều giao dịch liên tục"
    List<Transaction> findBySourceAccountAndTimestampBetween(
            String sourceAccount,
            Instant start,
            Instant end
    );
}