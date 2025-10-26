package com.bankmonitoring.tm_alerting.repository;

import com.bankmonitoring.tm_alerting.model.Alert;
import com.bankmonitoring.tm_alerting.model.AlertStatus;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AlertRepository extends MongoRepository<Alert, String> {
    List<Alert> findByStatus(AlertStatus status);
}