package com.vendingmachine.api;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.vendingmachine.domain.TransactionSession;
import com.vendingmachine.service.TransactionSessionService;

@RestController
@RequestMapping("/transaction-sessions")
public class TransactionSessionController {

    @Autowired
    private TransactionSessionService sessionService;

    @PostMapping("/start")
    public ResponseEntity<ApiResponse<TransactionSession>> startSession() {
        TransactionSession session = sessionService.startSession();
        ApiResponse<TransactionSession> response = new ApiResponse<>(session, 200, "Session started successfully", true);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/add-money")
    public ResponseEntity<ApiResponse<TransactionSession>> addMoney(@RequestParam Long sessionId, @RequestBody Map<String, Integer> moneyDetails) {
        TransactionSession session = sessionService.findById(sessionId)
            .orElseThrow(() -> new IllegalStateException("Session not found"));
        Integer denomination = moneyDetails.get("denomination");
        Integer quantity = moneyDetails.get("quantity");
        session = sessionService.addMoney(session, denomination, quantity);
        ApiResponse<TransactionSession> response = new ApiResponse<>(session, 200, "Money added successfully", true);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/current-total/{sessionId}")
    public ResponseEntity<ApiResponse<Integer>> getCurrentTotal(@PathVariable Long sessionId) {
        int total = sessionService.getTotalAmount(sessionId);
        ApiResponse<Integer> response = new ApiResponse<>(total, 200, "Current total retrieved successfully", true);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/refund")
    public ResponseEntity<ApiResponse<TransactionSession>> refund(@RequestParam Long sessionId) {
        TransactionSession session = sessionService.findById(sessionId)
            .orElseThrow(() -> new IllegalStateException("Session not found"));
        session = sessionService.refundMoney(session);
        ApiResponse<TransactionSession> response = new ApiResponse<>(session, 200, "Refund processed successfully", true);
        return ResponseEntity.ok(response);
    }
}
