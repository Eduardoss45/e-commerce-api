package com.api.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.order.domain.OrderEntity;

public interface OrderRepository extends JpaRepository<OrderEntity, String> {
}
