package com.vendingmachine.repository;

import com.vendingmachine.domain.TransactionSession;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionSessionRepository extends JpaRepository<TransactionSession, Long> {
}
