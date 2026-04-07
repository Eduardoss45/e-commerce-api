package com.api.order.service;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.api.order.domain.OrderEntity;
import com.api.order.dto.EventEnvelope;
import com.api.order.dto.InventoryFailedPayload;
import com.api.order.dto.InventoryReservedPayload;
import com.api.order.dto.OrderCancelledPayload;
import com.api.order.dto.OrderCreatedPayload;
import com.api.order.dto.OrderCompletedPayload;
import com.api.order.dto.OrderRequest;
import com.api.order.dto.OrderResponse;
import com.api.order.dto.PaymentDto;
import com.api.order.dto.PaymentFailedPayload;
import com.api.order.dto.PaymentProcessedPayload;
import com.api.order.dto.PaymentRequestedPayload;
import com.api.order.messaging.OrderEventPublisher;
import com.api.order.repository.OrderRepository;

@Service
public class OrderService {
    private final OrderRepository repository;
    private final OrderEventPublisher publisher;
    private final Map<String, PaymentDto> paymentCache = new ConcurrentHashMap<>();

    public OrderService(OrderRepository repository, OrderEventPublisher publisher) {
        this.repository = repository;
        this.publisher = publisher;
    }

    public OrderResponse createOrder(OrderRequest request) {
        String orderId = UUID.randomUUID().toString();

        OrderEntity entity = new OrderEntity(orderId, "CREATED", Instant.now());
        repository.save(entity);
        paymentCache.put(orderId, request.getPayment());

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

    public void onInventoryReserved(InventoryReservedPayload payload) {
        PaymentDto payment = paymentCache.get(payload.getOrderId());
        if (payment == null) {
            throw new IllegalStateException("Payment data not found for order " + payload.getOrderId());
        }
        PaymentRequestedPayload req = new PaymentRequestedPayload(payload.getOrderId(), payment);

        EventEnvelope<PaymentRequestedPayload> event = new EventEnvelope<>(
                UUID.randomUUID().toString(), "order", "payment.requested", Instant.now(), req);
        publisher.publishPaymentRequested(event);
    }

    public void onInventoryFailed(InventoryFailedPayload payload) {
        OrderEntity entity = repository.findById(payload.getOrderId())
                .orElse(null);
        if (entity != null) {
            entity.setStatus("CANCELLED");
            repository.save(entity);
        }
        OrderCancelledPayload cancelled = new OrderCancelledPayload(payload.getOrderId(), "CANCELLED");
        EventEnvelope<OrderCancelledPayload> event = new EventEnvelope<>(
                UUID.randomUUID().toString(), "order", "order.cancelled", Instant.now(), cancelled);
        publisher.publishOrderCancelled(event);
    }

    public void onPaymentProcessed(PaymentProcessedPayload payload) {
        OrderEntity entity = repository.findById(payload.getOrderId())
                .orElse(null);
        if (entity != null) {
            entity.setStatus("COMPLETED");
            repository.save(entity);
        }
        OrderCompletedPayload completed = new OrderCompletedPayload(payload.getOrderId(), "COMPLETED");
        EventEnvelope<OrderCompletedPayload> event = new EventEnvelope<>(
                UUID.randomUUID().toString(), "order", "order.completed", Instant.now(), completed);
        publisher.publishOrderCompleted(event);
    }

    public void onPaymentFailed(PaymentFailedPayload payload) {
        OrderEntity entity = repository.findById(payload.getOrderId())
                .orElse(null);
        if (entity != null) {
            entity.setStatus("CANCELLED");
            repository.save(entity);
        }
        OrderCancelledPayload cancelled = new OrderCancelledPayload(payload.getOrderId(), "CANCELLED");
        EventEnvelope<OrderCancelledPayload> event = new EventEnvelope<>(
                UUID.randomUUID().toString(), "order", "order.cancelled", Instant.now(), cancelled);
        publisher.publishOrderCancelled(event);
    }
}
