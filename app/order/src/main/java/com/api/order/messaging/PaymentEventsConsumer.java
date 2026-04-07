package com.api.order.messaging;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.api.order.dto.EventEnvelope;
import com.api.order.dto.PaymentFailedPayload;
import com.api.order.dto.PaymentProcessedPayload;
import com.api.order.service.OrderService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class PaymentEventsConsumer {
    private final ObjectMapper objectMapper;
    private final OrderService service;

    public PaymentEventsConsumer(ObjectMapper objectMapper, OrderService service) {
        this.objectMapper = objectMapper;
        this.service = service;
    }

    @RabbitListener(queues = "order.payment.queue")
    public void onPaymentProcessed(String message) {
        try {
            EventEnvelope<PaymentProcessedPayload> event = objectMapper.readValue(message,
                    new TypeReference<EventEnvelope<PaymentProcessedPayload>>() {
                    });
            service.onPaymentProcessed(event.getPayload());
        } catch (Exception ex) {
            throw new IllegalStateException("Failed to process payment.processed", ex);
        }
    }

    @RabbitListener(queues = "order.payment.fail.queue")
    public void onPaymentFailed(String message) {
        try {
            EventEnvelope<PaymentFailedPayload> event = objectMapper.readValue(message,
                    new TypeReference<EventEnvelope<PaymentFailedPayload>>() {
                    });
            service.onPaymentFailed(event.getPayload());
        } catch (Exception ex) {
            throw new IllegalStateException("Failed to process payment.failed", ex);
        }
    }
}
