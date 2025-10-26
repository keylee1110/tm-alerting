package com.bankmonitoring.tm_alerting.controller;

import com.bankmonitoring.tm_alerting.model.AuditLog;
import com.bankmonitoring.tm_alerting..AuditService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuditController {

    private final AuditService auditService;

    @GetMapping("/audit")
    public ResponseEntity<List<AuditLog>> getAudit() {
        return ResponseEntity.ok(auditService.getRecentLogs());
    }
}
