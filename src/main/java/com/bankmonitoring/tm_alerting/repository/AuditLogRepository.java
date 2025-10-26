package com.bankmonitoring.tm_alerting.repository;

import com.bankmonitoring.tm_alerting.model.AuditLog;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AuditLogRepository extends MongoRepository<AuditLog, String> {
    List<AuditLog> findAllByOrderByCreatedAtDesc();
}