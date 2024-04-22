package com.vendingmachine.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vendingmachine.domain.Product;
import com.vendingmachine.dto.ProductUpdateRequest;
import com.vendingmachine.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/products") 
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Product>>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        ApiResponse<List<Product>> response = new ApiResponse<>(products, 200, "Products retrieved successfully", true);
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/add")
    public ResponseEntity<ApiResponse<Product>> addProduct(@RequestBody Product product) {
        Product savedProduct = productService.addProduct(product);
        ApiResponse<Product> response = new ApiResponse<>(savedProduct, 201, "Product added successfully", true);
        return ResponseEntity.status(201).body(response);
    }
    
    @PutMapping("/{productId}/update")
    public ResponseEntity<ApiResponse<Product>> updateProduct(@PathVariable Long productId, @RequestBody ProductUpdateRequest updateRequest) {
        Product updatedProduct = productService.updateProduct(productId, updateRequest);
        ApiResponse<Product> response = new ApiResponse<>(updatedProduct, 200, "Product updated successfully", true);
        return ResponseEntity.ok(response);
    }
}