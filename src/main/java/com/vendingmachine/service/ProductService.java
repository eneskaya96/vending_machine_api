package com.vendingmachine.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import com.vendingmachine.domain.MoneyType;
import com.vendingmachine.domain.Product;
import com.vendingmachine.domain.TransactionSession;
import com.vendingmachine.dto.ProductUpdateRequest;
import com.vendingmachine.dto.PurchaseResult;
import com.vendingmachine.repository.MoneyTypeRepository;
import com.vendingmachine.repository.ProductRepository;
import com.vendingmachine.repository.TransactionSessionRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository repository;
    private final TransactionSessionRepository sessionRepository;
    private final MoneyTypeRepository moneyTypeRepository;

    @Autowired
    public ProductService(ProductRepository repository, TransactionSessionRepository sessionRepository, MoneyTypeRepository moneyTypeRepository) {
        this.repository = repository;
        this.sessionRepository = sessionRepository;
        this.moneyTypeRepository = moneyTypeRepository;
    }

    public List<Product> getAllProducts() {
        return repository.findAll();
    }
    
    public Product addProduct(Product product) {
        return repository.save(product); 
    }
    
    @Transactional
    public PurchaseResult purchaseProduct(Long sessionId, Long productId) {
        TransactionSession session = sessionRepository.findById(sessionId)
            .orElseThrow(() -> new IllegalStateException("Session not found"));
        Product product = repository.findById(productId)
            .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        if (product.getQuantity() < 1) {
            throw new IllegalStateException("Product is out of stock");
        }
        if (session.getTotalAmount() < product.getPrice()) {
            throw new IllegalStateException("Insufficient funds");
        }

        int remainingAmount = session.getTotalAmount() - product.getPrice();
        Map<Integer, Integer> changeMap = simulateChange(remainingAmount);
        if (changeMap == null) {
            throw new IllegalStateException("Unable to provide exact change");
        }
        
        
        applyChange(changeMap);
        incrementMoneyTypes(session.getMoneyInserted());
        
        
        session.setTotalAmount(0); // Reset the session balance as it is closed now
        product.setQuantity(product.getQuantity() - 1); // Decrement the product stock

        sessionRepository.save(session);
        repository.save(product);

        return new PurchaseResult(product, changeMap);
    }
    
    private void incrementMoneyTypes(Map<Integer, Integer> moneyInserted) {
        for (Map.Entry<Integer, Integer> entry : moneyInserted.entrySet()) {
            Optional<MoneyType> optionalMoneyType = moneyTypeRepository.findByDenomination(entry.getKey());
            if (optionalMoneyType.isPresent()) {
                MoneyType mt = optionalMoneyType.get();
                mt.setQuantity(mt.getQuantity() + entry.getValue());
                moneyTypeRepository.save(mt);
            } else {
                // Handle the case where no MoneyType is found for the given denomination
                // Create a new record if it does not exist
                MoneyType newMoneyType = new MoneyType();
                newMoneyType.setDenomination(entry.getKey());
                newMoneyType.setQuantity(entry.getValue());
                moneyTypeRepository.save(newMoneyType);
            }
        }
    }
    
    private Map<Integer, Integer> simulateChange(int change) {
        List<MoneyType> moneyTypes = moneyTypeRepository.findAllByOrderByDenominationDesc();
        Map<Integer, Integer> changeMap = new HashMap<>();
        int tempChange = change;

        for (MoneyType mt : moneyTypes) {
            int count = 0;
            while (tempChange >= mt.getDenomination() && count < mt.getQuantity()) {
                tempChange -= mt.getDenomination();
                count++;
            }
            if (count > 0) {
                changeMap.put(mt.getDenomination(), count);
            }
            if (tempChange == 0) break;
        }

        return tempChange == 0 ? changeMap : null;
    }

    private void applyChange(Map<Integer, Integer> changeMap) {
        for (Map.Entry<Integer, Integer> entry : changeMap.entrySet()) {
            Optional<MoneyType> optionalMoneyType = moneyTypeRepository.findByDenomination(entry.getKey());
            if (optionalMoneyType.isPresent()) {
                MoneyType mt = optionalMoneyType.get();
                mt.setQuantity(mt.getQuantity() - entry.getValue());
                moneyTypeRepository.save(mt);
            } else {
                // Handle the case where no MoneyType is found for the given denomination
                throw new IllegalArgumentException("Denomination not found: " + entry.getKey());
            }
        }
    }
    
    public Product updateProduct(Long productId, ProductUpdateRequest updateRequest) {
        Product product = repository.findById(productId)
            .orElseThrow(() -> new IllegalArgumentException("Product not found with ID: " + productId));

        if (updateRequest.getQuantity() != null) {
            product.setQuantity(updateRequest.getQuantity());
        }
        if (updateRequest.getPrice() != null) {
            product.setPrice(updateRequest.getPrice());
        }

        return repository.save(product);
    }


}
