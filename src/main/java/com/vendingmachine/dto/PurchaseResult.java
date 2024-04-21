package com.vendingmachine.dto;
import java.util.Map;

import com.vendingmachine.domain.Product;

public class PurchaseResult {
    private Product product;
    private Map<Integer, Integer> changeMap;

    public PurchaseResult(Product product, Map<Integer, Integer> changeMap) {
        this.product = product;
        this.changeMap = changeMap;
    }

    public Product getProduct() {
        return product;
    }

    public Map<Integer, Integer> getChangeMap() {
        return changeMap;
    }
}
