package com.api.order.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.order.dto.OrderRequest;
import com.api.order.dto.OrderResponse;
import com.api.order.service.OrderService;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<OrderResponse> create(@RequestBody OrderRequest request) {
        OrderResponse response = service.createOrder(request);
        return ResponseEntity.ok(response);
    }
}
