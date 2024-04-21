package com.vendingmachine.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;
    private Integer productId;
    private Integer amountPaid;
    private String status;
    private Integer changeReturned;

    // Constructors
    public Transaction() {
    }

    public Transaction(Integer productId, Integer amountPaid, String status, Integer changeReturned) {
        this.productId = productId;
        this.amountPaid = amountPaid;
        this.status = status;
        this.changeReturned = changeReturned;
    }

    // Getters and Setters
    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(Integer amountPaid) {
        this.amountPaid = amountPaid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getChangeReturned() {
        return changeReturned;
    }

    public void setChangeReturned(Integer changeReturned) {
        this.changeReturned = changeReturned;
    }
}
