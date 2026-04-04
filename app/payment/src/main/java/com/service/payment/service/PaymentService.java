package com.service.payment.service;

import java.util.UUID;

import com.service.payment.dto.InventoryReservedPayload;
import com.service.payment.dto.PaymentFailedPayload;
import com.service.payment.dto.PaymentProcessedPayload;
import com.service.payment.messaging.PaymentEventPublisher;

public class PaymentService {
    private final PaymentEventPublisher publisher;

    public PaymentService(PaymentEventPublisher publisher) {
        this.publisher = publisher;
    }

    public void process(InventoryReservedPayload payload) {
        boolean approved = true;

        if (approved) {
            PaymentProcessedPayload ok = new PaymentProcessedPayload(
                    payload.getOrderId(),
                    UUID.randomUUID().toString(),
                    "APPROVED");
            publisher.publishProcessed(ok);
        } else {
            PaymentFailedPayload fail = new PaymentFailedPayload(
                    payload.getOrderId(),
                    UUID.randomUUID().toString(),
                    "DECLINED");
            publisher.publishFailed(fail);
        }
    }
}
