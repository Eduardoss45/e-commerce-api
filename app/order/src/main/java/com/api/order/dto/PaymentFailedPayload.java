package com.api.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentFailedPayload {
    private String orderId;
    private String paymentId;
    private String status;
}
