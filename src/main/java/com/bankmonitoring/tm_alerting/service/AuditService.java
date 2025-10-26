package com.bankmonitoring.tm_alerting.service;

import com.bankmonitoring.tm_alerting.model.AuditLog;
import com.bankmonitoring.tm_alerting.repository.AuditLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuditService {

    private final AuditLogRepository auditLogRepository;

    public void logAction(String action, String targetId, String message, Authentication auth) {
        var actorEmail = (auth != null ? auth.getName() : "system");

        var log = AuditLog.builder()
                .action(action)
                .targetId(targetId)
                .actor(actorEmail)
                .message(message)
                .createdAt(Instant.now())
                .build();

        auditLogRepository.save(log);
    }

    public List<AuditLog> getRecentLogs() {
        return auditLogRepository.findAllByOrderByCreatedAtDesc();
    }

    public List<AuditLog> getRecentLogsLimited(int limit) {
        return auditLogRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .limit(limit)
                .toList();
    }
}