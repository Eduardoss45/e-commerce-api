package com.service.payment.messaging;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.payment.dto.EventEnvelope;
import com.service.payment.dto.PaymentRequestedPayload;
import com.service.payment.service.PaymentService;

@Component
public class PaymentConsumer {
    private final ObjectMapper objectMapper;
    private final PaymentService service;

    public PaymentConsumer(ObjectMapper objectMapper, PaymentService service) {
        this.objectMapper = objectMapper;
        this.service = service;
    }

    @RabbitListener(queues = "payment.queue")
    public void onPaymentRequested(String message) {
        try {
            EventEnvelope<PaymentRequestedPayload> event = objectMapper.readValue(message,
                    new TypeReference<EventEnvelope<PaymentRequestedPayload>>() {
                    });
            service.process(event.getPayload());
        } catch (Exception ex) {
            throw new IllegalStateException("Failed to process payment.requested", ex);
        }
    }
}
