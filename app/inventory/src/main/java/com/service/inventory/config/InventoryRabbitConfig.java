package com.service.inventory.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InventoryRabbitConfig {
    @Bean
    public DirectExchange orderExchange() {
        return new DirectExchange("order.exchange", true, false);
    }

    @Bean
    public DirectExchange inventoryExchange() {
        return new DirectExchange("inventory.exchange", true, false);
    }

    @Bean
    public Queue inventoryQueue() {
        return new Queue("inventory.queue", true);
    }

    @Bean
    public Binding inventoryBinding(Queue inventoryQueue, DirectExchange orderExchange) {
        return BindingBuilder.bind(inventoryQueue).to(orderExchange).with("order.created");
    }
}
