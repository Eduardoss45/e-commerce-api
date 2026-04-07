package com.service.payment.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    @Bean
    public DirectExchange paymentExchange() {
        return new DirectExchange("payment.exchange", true, false);
    }

    @Bean
    public Queue paymentQueue() {
        return new Queue("payment.queue", true);
    }

    @Bean
    public Binding paymentRequestedBinding(Queue paymentQueue, DirectExchange paymentExchange) {
        return BindingBuilder.bind(paymentQueue).to(paymentExchange).with("payment.requested");
    }
}
