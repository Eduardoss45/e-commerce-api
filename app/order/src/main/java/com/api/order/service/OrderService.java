package com.api.order.service;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import com.api.order.dto.EventEnvelope;
import com.api.order.dto.OrderCreatedPayload;
import com.api.order.dto.OrderRequest;
import com.api.order.dto.OrderResponse;
import com.api.order.messaging.OrderEventPublisher;

@Service
public class OrderService {
    private final OrderEventPublisher publisher;
    private final Map<String, OrderResponse> memoryStore = new ConcurrentHashMap<>();

    public OrderService(OrderEventPublisher publisher) {
        this.publisher = publisher;
    }

    public OrderResponse createOrder(OrderRequest request) {
        String orderId = UUID.randomUUID().toString();

        OrderCreatedPayload payload = new OrderCreatedPayload(orderId, request.getItems());

        EventEnvelope<OrderCreatedPayload> event = new EventEnvelope<>(
            UUID.randomUUID().toString(),
            "order",
            "order.created",
            Instant.now(),
            payload
        );

        publisher.publishOrderCreated(event);

        OrderResponse response = new OrderResponse(orderId, "CREATED");
        memoryStore.put(orderId, response);
        return response;
    }
}
