package com.bankmonitoring.tm_alerting.service;

import com.bankmonitoring.tm_alerting.controller.dto.AlertDtos.*;
import com.bankmonitoring.tm_alerting.model.Alert;
import com.bankmonitoring.tm_alerting.model.AlertStatus;
import com.bankmonitoring.tm_alerting.repository.AlertRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AlertService {

    private final AlertRepository alertRepository;
    private final AuditService auditService;

    public List<AlertView> getAllAlerts() {
        return alertRepository.findAll()
                .stream()
                .map(this::toView)
                .toList();
    }

    public List<AlertView> getAlertsFiltered(String statusFilter) {
        var list = switch (statusFilter == null ? "ALL" : statusFilter.toUpperCase()) {
            case "PENDING"  -> alertRepository.findByStatus(AlertStatus.PENDING);
            case "RESOLVED" -> alertRepository.findByStatus(AlertStatus.RESOLVED);
            default         -> alertRepository.findAll();
        };

        return list.stream().map(this::toView).toList();
    }

    public AlertView resolveAlert(String id, ResolveRequest req, Authentication auth) {
        // 1. Kiểm tra quyền ADMIN
        boolean isAdmin = auth != null && auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority) // vd "ROLE_ADMIN"
                .anyMatch(roleStr -> roleStr.equals("ROLE_ADMIN"));

        if (!isAdmin) {
            throw new RuntimeException("FORBIDDEN: ADMIN only");
        }

        // 2. Cập nhật trạng thái
        var alert = alertRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Alert not found"));

        if (req != null && req.getStatus() != null) {
            alert.setStatus(AlertStatus.valueOf(req.getStatus().toUpperCase()));
        } else {
            alert.setStatus(AlertStatus.RESOLVED);
        }

        alertRepository.save(alert);

        // 3. Ghi audit log
        auditService.logAction(
                "RESOLVE_ALERT",
                alert.getId(),
                "Alert " + alert.getId() + " marked " + alert.getStatus(),
                auth
        );

        return toView(alert);
    }

    private AlertView toView(Alert a) {
        return AlertView.builder()
                .id(a.getId())
                .transactionId(a.getTransactionId())
                .type(a.getType())
                .message(a.getMessage())
                .status(a.getStatus())
                .createdAt(a.getCreatedAt())
                .build();
    }
}
