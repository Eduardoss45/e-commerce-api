package com.api.order.messaging;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.api.order.dto.EventEnvelope;
import com.api.order.dto.InventoryFailedPayload;
import com.api.order.dto.InventoryReservedPayload;
import com.api.order.service.OrderService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class InventoryEventsConsumer {
    private final ObjectMapper objectMapper;
    private final OrderService service;

    public InventoryEventsConsumer(ObjectMapper objectMapper, OrderService service) {
        this.objectMapper = objectMapper;
        this.service = service;
    }

    @RabbitListener(queues = "order.queue")
    public void onInventoryReserved(String message) {
        try {
            EventEnvelope<InventoryReservedPayload> event = objectMapper.readValue(message,
                    new TypeReference<EventEnvelope<InventoryReservedPayload>>() {
                    });
            service.onInventoryReserved(event.getPayload());
        } catch (Exception ex) {
            throw new IllegalStateException("Failed to process inventory.reserved", ex);
        }
    }

    @RabbitListener(queues = "order.fail.queue")
    public void onInventoryFailed(String message) {
        try {
            EventEnvelope<InventoryFailedPayload> event = objectMapper.readValue(message,
                    new TypeReference<EventEnvelope<InventoryFailedPayload>>() {
                    });
            service.onInventoryFailed(event.getPayload());
        } catch (Exception ex) {
            throw new IllegalStateException("Failed to process inventory.failed", ex);
        }
    }
}
