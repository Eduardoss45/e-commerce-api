package com.api.order.messaging;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import com.api.order.dto.EventEnvelope;
import com.api.order.dto.OrderCancelledPayload;
import com.api.order.dto.OrderCompletedPayload;
import com.api.order.dto.OrderCreatedPayload;
import com.api.order.dto.PaymentRequestedPayload;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class OrderEventPublisher {

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    public OrderEventPublisher(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    public void publishOrderCreated(EventEnvelope<OrderCreatedPayload> event) {
        rabbitTemplate.convertAndSend("order.exchange", "order.created", toJson(event));
    }

    public void publishPaymentRequested(EventEnvelope<PaymentRequestedPayload> event) {
        rabbitTemplate.convertAndSend("payment.exchange", "payment.requested", toJson(event));
    }

    public void publishOrderCompleted(EventEnvelope<OrderCompletedPayload> event) {
        rabbitTemplate.convertAndSend("order.exchange", "order.completed", toJson(event));
    }

    public void publishOrderCancelled(EventEnvelope<OrderCancelledPayload> event) {
        rabbitTemplate.convertAndSend("order.exchange", "order.cancelled", toJson(event));
    }

    private String toJson(Object value) {
        try {
            return objectMapper.writeValueAsString(value);
        } catch (JsonProcessingException ex) {
            throw new IllegalStateException("Failed to serialize event", ex);
        }
    }
}
