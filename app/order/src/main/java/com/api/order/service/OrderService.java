package com.api.order.service;

import java.time.Instant;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.api.order.domain.OrderEntity;
import com.api.order.dto.EventEnvelope;
import com.api.order.dto.OrderCreatedPayload;
import com.api.order.dto.OrderRequest;
import com.api.order.dto.OrderResponse;
import com.api.order.messaging.OrderEventPublisher;
import com.api.order.repository.OrderRepository;

@Service
public class OrderService {
    private final OrderRepository repository;
    private final OrderEventPublisher publisher;

    public OrderService(OrderRepository repository, OrderEventPublisher publisher) {
        this.repository = repository;
        this.publisher = publisher;
    }

    public OrderResponse createOrder(OrderRequest request) {
        String orderId = UUID.randomUUID().toString();

        OrderEntity entity = new OrderEntity(orderId, "CREATED", Instant.now());
        repository.save(entity);

        OrderCreatedPayload payload = new OrderCreatedPayload(orderId, request.getItems());

        EventEnvelope<OrderCreatedPayload> event = new EventEnvelope<>(
                UUID.randomUUID().toString(),
                "order",
                "order.created",
                Instant.now(),
                payload);
        publisher.publishOrderCreated(event);

        return new OrderResponse(orderId, "CREATED");
    }
}
