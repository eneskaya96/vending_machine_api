package com.vendingmachine.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
public class MoneyType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long moneyTypeID;
    private Integer denomination;
    private Integer quantity;

    // Constructors
    public MoneyType() {
    }

    public MoneyType(Integer denomination, Integer quantity) {
        this.denomination = denomination;
        this.quantity = quantity;
    }

    // Getters and Setters
    public Long getMoneyTypeID() {
        return moneyTypeID;
    }

    public void setMoneyTypeID(Long moneyTypeID) {
        this.moneyTypeID = moneyTypeID;
    }

    public Integer getDenomination() {
        return denomination;
    }

    public void setDenomination(Integer denomination) {
        this.denomination = denomination;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}