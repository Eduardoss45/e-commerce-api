package com.service.inventory.messaging;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.inventory.dto.EventEnvelope;
import com.service.inventory.dto.InventoryFailedPayload;
import com.service.inventory.dto.InventoryReservedPayload;

@Component
public class InventoryEventPublisher {
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    public InventoryEventPublisher(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    public void publishReserved(EventEnvelope<InventoryReservedPayload> payload) {
        send("inventory.exchange", "inventory.reserved", payload);
    }

    public void publishFailed(EventEnvelope<InventoryFailedPayload> payload) {
        send("inventory.exchange", "inventory.failed", payload);
    }

    private void send(String exchange, String routingKey, Object payload) {
        try {
            String json = objectMapper.writeValueAsString(payload);
            rabbitTemplate.convertAndSend(exchange, routingKey, json);
        } catch (Exception ex) {
            throw new IllegalStateException("Failed to publish inventory event", ex);
        }
    }
}
