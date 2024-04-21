package com.vendingmachine.repository;

import com.vendingmachine.domain.MoneyType;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MoneyTypeRepository extends JpaRepository<MoneyType, Long> {
	 List<MoneyType> findAllByOrderByDenominationDesc();
	 Optional<MoneyType> findByDenomination(Integer denomination); 
}
