package com.vendingmachine.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.MapKeyColumn;

import java.util.Map;

@Entity
public class TransactionSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sessionId;
    private Integer totalAmount;

    @ElementCollection // This annotation is used to define a collection of elements
    @CollectionTable(name = "money_insertions") // This names the table used to store the map
    @MapKeyColumn(name = "denomination") // This column stores the keys of the map (denominations)
    @Column(name = "quantity") // This column stores the values of the map (quantities)
    private Map<Integer, Integer> moneyInserted; // Map of denomination to quantity of money inserted

    // Constructors
    public TransactionSession() {
    }

    public TransactionSession(Integer totalAmount, Map<Integer, Integer> moneyInserted) {
        this.totalAmount = totalAmount;
        this.moneyInserted = moneyInserted;
    }

    // Getters and Setters
    public Long getSessionId() {
        return sessionId;
    }

    public void setSessionId(Long sessionId) {
        this.sessionId = sessionId;
    }

    public Integer getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Integer totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Map<Integer, Integer> getMoneyInserted() {
        return moneyInserted;
    }

    public void setMoneyInserted(Map<Integer, Integer> moneyInserted) {
        this.moneyInserted = moneyInserted;
    }
}
