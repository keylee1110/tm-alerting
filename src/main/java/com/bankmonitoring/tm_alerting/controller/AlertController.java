package com.bankmonitoring.tm_alerting.controller;

import com.bankmonitoring.tm_alerting.controller.dto.AlertDtos.*;
import com.bankmonitoring.tm_alerting.service.AlertService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AlertController {

    private final AlertService alertService;

    @GetMapping("/alerts")
    public ResponseEntity<List<AlertView>> getAlerts() {
        return ResponseEntity.ok(alertService.getAllAlerts());
    }

    @PatchMapping("/alerts/{id}/resolve")
    public ResponseEntity<AlertView> resolve(
            @PathVariable String id,
            @RequestBody(required = false) ResolveRequest req,
            Authentication auth // lấy từ SecurityContext
    ) {
        var result = alertService.resolveAlert(id, req, auth);
        return ResponseEntity.ok(result);
    }
}
