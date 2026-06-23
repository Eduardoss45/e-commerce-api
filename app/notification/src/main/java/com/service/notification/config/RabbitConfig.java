package com.service.notification.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
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
    public Queue notificationQueue() {
        return new Queue("notification.queue", true);
    }

    @Bean
    public Binding orderCompletedBinding(
            Queue notificationQueue,
            DirectExchange orderExchange) {

        return BindingBuilder
                .bind(notificationQueue)
                .to(orderExchange)
                .with("order.completed");
    }

    @Bean
    public Binding orderCancelledBinding(
            Queue notificationQueue,
            DirectExchange orderExchange) {

        return BindingBuilder
                .bind(notificationQueue)
                .to(orderExchange)
                .with("order.cancelled");
    }
}