package com.service.payment.service;

import java.time.Instant;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.service.payment.dto.EventEnvelope;
import com.service.payment.dto.PaymentFailedPayload;
import com.service.payment.dto.PaymentDto;
import com.service.payment.dto.PaymentProcessedPayload;
import com.service.payment.dto.PaymentRequestedPayload;
import com.service.payment.messaging.PaymentEventPublisher;

@Service
public class PaymentService {
    private final PaymentEventPublisher publisher;

    public PaymentService(PaymentEventPublisher publisher) {
        this.publisher = publisher;
    }

    public void process(PaymentRequestedPayload payload) {
        PaymentDto payment = payload.getPayment();
        if (!isValidPayment(payment)) {
            PaymentFailedPayload fail = new PaymentFailedPayload(
                    payload.getOrderId(),
                    UUID.randomUUID().toString(),
                    "DECLINED");
            EventEnvelope<PaymentFailedPayload> event = new EventEnvelope<>(
                    UUID.randomUUID().toString(),
                    "payment",
                    "payment.failed",
                    Instant.now(),
                    fail);
            publisher.publishFailed(event);
            return;
        }

        if (isForcedFailure(payment)) {
            PaymentFailedPayload fail = new PaymentFailedPayload(
                    payload.getOrderId(),
                    UUID.randomUUID().toString(),
                    "DECLINED");
            EventEnvelope<PaymentFailedPayload> event = new EventEnvelope<>(
                    UUID.randomUUID().toString(),
                    "payment",
                    "payment.failed",
                    Instant.now(),
                    fail);
            publisher.publishFailed(event);
        } else {
            PaymentProcessedPayload ok = new PaymentProcessedPayload(
                    payload.getOrderId(),
                    UUID.randomUUID().toString(),
                    "APPROVED");
            EventEnvelope<PaymentProcessedPayload> event = new EventEnvelope<>(
                    UUID.randomUUID().toString(),
                    "payment",
                    "payment.processed",
                    Instant.now(),
                    ok);
            publisher.publishProcessed(event);
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
