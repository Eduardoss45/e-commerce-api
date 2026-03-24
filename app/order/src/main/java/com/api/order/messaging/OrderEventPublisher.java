package com.api.order.messaging;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import com.api.order.dto.EventEnvelope;
import com.api.order.dto.OrderCreatedPayload;

@Component
public class OrderEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    public OrderEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publishOrderCreated(EventEnvelope<OrderCreatedPayload> event) {
        rabbitTemplate.convertAndSend("order.exchange", "order.created", event);
    }
}
