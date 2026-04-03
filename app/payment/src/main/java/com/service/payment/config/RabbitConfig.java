package com.service.payment.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    @Bean
    public Queue inventoryReservedQueue() {
        return new Queue("inventory.reserved.queue");
    }
}
