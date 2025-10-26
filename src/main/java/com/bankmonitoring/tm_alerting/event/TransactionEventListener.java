package com.bankmonitoring.tm_alerting.event;

import com.bankmonitoring.tm_alerting.model.Alert;
import com.bankmonitoring.tm_alerting.model.Transaction;
import com.bankmonitoring.tm_alerting.service.AuditService;
import com.bankmonitoring.tm_alerting.service.TransactionRuleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class TransactionEventListener {

    private final TransactionRuleService transactionRuleService;
    private final AuditService auditService;

    @EventListener
    public void onTransactionCreated(TransactionCreatedEvent event) {
        Transaction txn = event.getTransaction();

        // 1. chạy rule và sinh alert
        List<Alert> alerts = transactionRuleService.evaluateAndCreateAlerts(txn);

        // 2. audit log (actor = system vì giao dịch có thể đến từ batch/integration)
        auditService.logAction(
                "CREATE_TRANSACTION",
                txn.getId(),
                "Transaction " + txn.getId() +
                        " created, alerts=" + alerts.size(),
                null // auth null -> actor=system
        );

        log.info("Processed txn {} -> {} alert(s)", txn.getId(), alerts.size());
    }
}
