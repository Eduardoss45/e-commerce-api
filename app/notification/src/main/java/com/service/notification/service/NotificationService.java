package com.service.notification.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.service.notification.dto.OrderCancelledPayload;
import com.service.notification.dto.OrderCompletedPayload;

@Service
public class NotificationService {
    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);

    public void notifyCompleted(OrderCompletedPayload payload) {
        logger.info("Notification: order {} completed", payload.getOrderId());
    }

    public void notifyCancelled(OrderCancelledPayload payload) {
        logger.info("Notification: order {} cancelled", payload.getOrderId());
    }
}
