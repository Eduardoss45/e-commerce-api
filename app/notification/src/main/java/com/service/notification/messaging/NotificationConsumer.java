package com.service.notification.messaging;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.notification.dto.EventEnvelope;
import com.service.notification.dto.OrderCancelledPayload;
import com.service.notification.dto.OrderCompletedPayload;
import com.service.notification.service.NotificationService;

@Component
public class NotificationConsumer {
    private final ObjectMapper objectMapper;
    private final NotificationService service;

    public NotificationConsumer(ObjectMapper objectMapper, NotificationService service) {
        this.objectMapper = objectMapper;
        this.service = service;
    }

    @RabbitListener(queues = "notification.queue")
    public void onOrderCompleted(String message) {
        try {
            EventEnvelope<OrderCompletedPayload> event = objectMapper.readValue(message,
                    new TypeReference<EventEnvelope<OrderCompletedPayload>>() {
                    });
            service.notifyCompleted(event.getPayload());
        } catch (Exception ex) {
            throw new IllegalStateException("Failed to process order.completed", ex);
        }
    }

    @RabbitListener(queues = "notification.queue")
    public void onOrderCancelled(String message) {
        try {
            EventEnvelope<OrderCancelledPayload> event = objectMapper.readValue(message,
                    new TypeReference<EventEnvelope<OrderCancelledPayload>>() {
                    });
            service.notifyCancelled(event.getPayload());
        } catch (Exception ex) {
            throw new IllegalStateException("Failed to process order.cancelled", ex);
        }
    }
}
