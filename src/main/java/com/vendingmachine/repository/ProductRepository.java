package com.vendingmachine.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vendingmachine.domain.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}