package com.vendingmachine.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vendingmachine.domain.MoneyType;
import com.vendingmachine.dto.UpdateQuantityRequest;
import com.vendingmachine.service.MoneyTypeService;
import java.util.List;

@RestController
@RequestMapping("/money-types")
public class MoneyTypeController {

    private final MoneyTypeService moneyTypeService;

    @Autowired
    public MoneyTypeController(MoneyTypeService moneyTypeService) {
        this.moneyTypeService = moneyTypeService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<MoneyType>>> getAllMoneyTypes() {
        List<MoneyType> moneyTypes = moneyTypeService.findAllMoneyTypes();
        ApiResponse<List<MoneyType>> response = new ApiResponse<>(moneyTypes, 200, "Money types retrieved successfully", true);
        return ResponseEntity.ok(response);
    }
    
    // Update quantity for a specific MoneyType
    @PutMapping("/{moneyTypeId}/update-quantity")
    public ResponseEntity<?> updateMoneyTypeQuantity(@PathVariable Long moneyTypeId,  @RequestBody UpdateQuantityRequest request) {
        try {
            MoneyType updatedMoneyType = moneyTypeService.updateQuantity(moneyTypeId, request.getQuantity());
            return ResponseEntity.ok(updatedMoneyType);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error updating money type quantity: " + e.getMessage());
        }
    }
}
