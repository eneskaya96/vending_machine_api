package com.vendingmachine.repository;

import com.vendingmachine.domain.MoneyType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MoneyTypeRepository extends JpaRepository<MoneyType, Long> {
}
