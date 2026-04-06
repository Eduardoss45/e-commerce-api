package com.service.payment.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.service.payment.dto.InventoryReservedPayload;
import com.service.payment.dto.PaymentFailedPayload;
import com.service.payment.dto.PaymentDto;
import com.service.payment.dto.PaymentProcessedPayload;
import com.service.payment.messaging.PaymentEventPublisher;

@Service
public class PaymentService {
    private final PaymentEventPublisher publisher;

    public PaymentService(PaymentEventPublisher publisher) {
        this.publisher = publisher;
    }

    public void process(InventoryReservedPayload payload) {
        PaymentDto payment = payload.getPayment();
        if (!isValidPayment(payment)) {
            PaymentFailedPayload fail = new PaymentFailedPayload(
                    payload.getOrderId(),
                    UUID.randomUUID().toString(),
                    "DECLINED");
            publisher.publishFailed(fail);
            return;
        }

        if (isForcedFailure(payment)) {
            PaymentFailedPayload fail = new PaymentFailedPayload(
                    payload.getOrderId(),
                    UUID.randomUUID().toString(),
                    "DECLINED");
            publisher.publishFailed(fail);
        } else {
            PaymentProcessedPayload ok = new PaymentProcessedPayload(
                    payload.getOrderId(),
                    UUID.randomUUID().toString(),
                    "APPROVED");
            publisher.publishProcessed(ok);
        }
    }

    private boolean isValidPayment(PaymentDto payment) {
        if (payment == null) {
            return false;
        }
        if (payment.getCardNumber() == null || payment.getCvv() == null) {
            return false;
        }
        if (!payment.getCardNumber().matches("\\d{13,19}")) {
            return false;
        }
        return payment.getCvv().matches("\\d{3}");
    }

    private boolean isForcedFailure(PaymentDto payment) {
        return "100".equals(payment.getCvv());
    }
}
