package com.api.order.config;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    @Bean
    public DirectExchange orderExchange() {
        return new DirectExchange("order.exchange", true, false);
    }

    @Bean
    public DirectExchange inventoryExchange() {
        return new DirectExchange("inventory.exchange", true, false);
    }

    @Bean
    public DirectExchange paymentExchange() {
        return new DirectExchange("payment.exchange", true, false);
    }

    @Bean
    public Queue orderQueue() {
        return new Queue("order.queue", true);
    }

    @Bean
    public Queue orderFailQueue() {
        return new Queue("order.fail.queue", true);
    }

    @Bean
    public Queue orderPaymentQueue() {
        return new Queue("order.payment.queue", true);
    }

    @Bean
    public Queue orderPaymentFailQueue() {
        return new Queue("order.payment.fail.queue", true);
    }

    @Bean
    public Binding inventoryReservedBinding(Queue orderQueue, DirectExchange inventoryExchange) {
        return BindingBuilder.bind(orderQueue).to(inventoryExchange).with("inventory.reserved");
    }

    @Bean
    public Binding inventoryFailedBinding(Queue orderFailQueue, DirectExchange inventoryExchange) {
        return BindingBuilder.bind(orderFailQueue).to(inventoryExchange).with("inventory.failed");
    }

    @Bean
    public Binding paymentProcessedBinding(Queue orderPaymentQueue, DirectExchange paymentExchange) {
        return BindingBuilder.bind(orderPaymentQueue).to(paymentExchange).with("payment.processed");
    }

    @Bean
    public Binding paymentFailedBinding(Queue orderPaymentFailQueue, DirectExchange paymentExchange) {
        return BindingBuilder.bind(orderPaymentFailQueue).to(paymentExchange).with("payment.failed");
    }
}
