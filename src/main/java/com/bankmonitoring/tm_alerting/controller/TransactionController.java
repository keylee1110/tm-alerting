package com.bankmonitoring.tm_alerting.controller;

import com.bankmonitoring.tm_alerting.controller.dto.TransactionDtos.*;
import com.bankmonitoring.tm_alerting.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("/transactions")
    public ResponseEntity<TransactionResponse> createTxn(@RequestBody TransactionRequest req) {
        var res = transactionService.createTransaction(req);
        return ResponseEntity.ok(res);
    }
}