package com.vendingmachine.service;

import com.vendingmachine.domain.MoneyType;
import com.vendingmachine.repository.MoneyTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MoneyTypeService {

    @Autowired
    private MoneyTypeRepository moneyTypeRepository;

    public MoneyType saveMoneyType(MoneyType moneyType) {
        return moneyTypeRepository.save(moneyType);
    }

    public List<MoneyType> findAllMoneyTypes() {
        return moneyTypeRepository.findAll();
    }

    public MoneyType updateMoneyTypeQuantity(Long id, Integer quantity) {
        MoneyType moneyType = moneyTypeRepository.findById(id).orElseThrow(() -> new RuntimeException("MoneyType not found"));
        moneyType.setQuantity(quantity);
        return moneyTypeRepository.save(moneyType);
    }

    public void deleteMoneyType(Long id) {
        moneyTypeRepository.deleteById(id);
    }
    
    public MoneyType updateQuantity(Long moneyTypeId, int quantity) {
        MoneyType moneyType = moneyTypeRepository.findById(moneyTypeId)
            .orElseThrow(() -> new IllegalStateException("MoneyType not found with ID: " + moneyTypeId));

        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative.");
        }

        moneyType.setQuantity(quantity);
        return moneyTypeRepository.save(moneyType);
    }
}
