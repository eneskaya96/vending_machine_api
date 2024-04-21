package com.vendingmachine.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vendingmachine.domain.Transaction;
import com.vendingmachine.service.TransactionService;
import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<Transaction>> createTransaction(@RequestBody Transaction transaction) {
        Transaction createdTransaction = transactionService.createTransaction(transaction);
        ApiResponse<Transaction> response = new ApiResponse<>(createdTransaction, 201, "Transaction created successfully", true);
        return ResponseEntity.status(201).body(response);
    }

    // You can add more endpoints here for updating, deleting, or handling specific transaction actions like canceling or completing a transaction.
}
