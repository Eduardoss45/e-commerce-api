package com.service.inventory.messaging;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.inventory.dto.EventEnvelope;
import com.service.inventory.dto.OrderCreatedPayload;
import com.service.inventory.service.InventoryService;

@Component
public class InventoryConsumer {
    private final ObjectMapper objectMapper;
    private final InventoryService service;

    public InventoryConsumer(ObjectMapper objectMapper, InventoryService service) {
        this.objectMapper = objectMapper;
        this.service = service;
    }

    @RabbitListener(queues = "inventory.queue")
    public void onOrderCreated(String message) {
        try {
            EventEnvelope<OrderCreatedPayload> event = objectMapper.readValue(message,
                    new TypeReference<EventEnvelope<OrderCreatedPayload>>() {
                    });
            service.reserve(event.getPayload());
        } catch (Exception ex) {
            throw new IllegalStateException("Failed to process order.created", ex);
        }
    }
}
