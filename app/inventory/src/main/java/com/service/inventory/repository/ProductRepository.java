package com.service.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.service.inventory.domain.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
