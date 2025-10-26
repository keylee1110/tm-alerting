package com.bankmonitoring.tm_alerting.event;

import com.bankmonitoring.tm_alerting.model.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DomainEventPublisher {

    private final ApplicationEventPublisher springPublisher;

    public void publishTransactionCreated(Transaction txn) {
        springPublisher.publishEvent(new TransactionCreatedEvent(this, txn));
    }
}
