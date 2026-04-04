package com.service.payment.messaging;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.payment.dto.PaymentFailedPayload;
import com.service.payment.dto.PaymentProcessedPayload;

@Component
public class PaymentEventPublisher {
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    public PaymentEventPublisher(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    public void publishProcessed(PaymentProcessedPayload payload) {
        send("payment.exchange", "payment.processed", payload);
    }

    public void publishFailed(PaymentFailedPayload payload) {
        send("payment.exchange", "payment.failed", payload);
    }

    private void send(String exchange, String routingKey, Object payload) {
        try {
            String json = objectMapper.writeValueAsString(payload);
            rabbitTemplate.convertAndSend(exchange, routingKey, json);
        } catch (Exception ex) {
            throw new IllegalStateException("Failed to publish event", ex);
        }
    }
}
