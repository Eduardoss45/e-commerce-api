package com.service.inventory.service;

import java.time.Instant;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.service.inventory.domain.Product;
import com.service.inventory.dto.EventEnvelope;
import com.service.inventory.dto.InventoryFailedPayload;
import com.service.inventory.dto.InventoryReservedPayload;
import com.service.inventory.dto.ItemDto;
import com.service.inventory.dto.OrderCreatedPayload;
import com.service.inventory.messaging.InventoryEventPublisher;
import com.service.inventory.repository.ProductRepository;

@Service
public class InventoryService {
    private final InventoryEventPublisher publisher;
    private final ProductRepository productRepository;

    public InventoryService(InventoryEventPublisher publisher, ProductRepository productRepository) {
        this.publisher = publisher;
        this.productRepository = productRepository;
    }

    public void reserve(OrderCreatedPayload payload) {
        for (ItemDto item : payload.getItems()) {
            Product product = productRepository.findById(item.getProductId())
                    .orElse(null);

            if (product == null || product.getStock() < item.getQuantity()) {
                InventoryFailedPayload failed = new InventoryFailedPayload(
                        payload.getOrderId(),
                        "OUT_OF_STOCK");
                EventEnvelope<InventoryFailedPayload> event = new EventEnvelope<>(
                        UUID.randomUUID().toString(),
                        "inventory",
                        "inventory.failed",
                        Instant.now(),
                        failed);
                publisher.publishFailed(event);
                return;
            }
        }

        InventoryReservedPayload reserved = new InventoryReservedPayload(
                payload.getOrderId(),
                payload.getItems());
        EventEnvelope<InventoryReservedPayload> event = new EventEnvelope<>(
                UUID.randomUUID().toString(),
                "inventory",
                "inventory.reserved",
                Instant.now(),
                reserved);
        publisher.publishReserved(event);
    }
}
