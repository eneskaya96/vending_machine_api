package com.vendingmachine.service;

import com.vendingmachine.domain.MoneyType;
import com.vendingmachine.domain.Product;
import com.vendingmachine.domain.TransactionSession;
import com.vendingmachine.dto.PurchaseResult;
import com.vendingmachine.repository.MoneyTypeRepository;
import com.vendingmachine.repository.ProductRepository;
import com.vendingmachine.repository.TransactionSessionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;
    
    @Mock
    private TransactionSessionRepository sessionRepository;
    
    @Mock
    private MoneyTypeRepository moneyTypeRepository;
    
    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void setUp() {
        lenient().when(moneyTypeRepository.findByDenomination(1)).thenReturn(Optional.of(new MoneyType(1, 10)));
        lenient().when(moneyTypeRepository.findByDenomination(5)).thenReturn(Optional.of(new MoneyType(5, 20)));
    }


    @Test
    void testGetAllProducts() {
        when(productRepository.findAll()).thenReturn(Arrays.asList(new Product(), new Product()));
        List<Product> productList = productService.getAllProducts();
        assertEquals(2, productList.size());
        verify(productRepository).findAll();
    }

    @Test
    void testAddProduct() {
        Product product = new Product();
        when(productRepository.save(any(Product.class))).thenReturn(product);
        Product savedProduct = productService.addProduct(product);
        assertNotNull(savedProduct);
        verify(productRepository).save(product);
    }

    @Test
    void testPurchaseProductSuccessful() {
        Long sessionId = 1L;
        Long productId = 1L;
        TransactionSession session = new TransactionSession();
        session.setTotalAmount(100);
        session.setMoneyInserted(new HashMap<>()); 
        
        Product product = new Product();
        product.setPrice(50);
        product.setQuantity(10);

        when(sessionRepository.findById(sessionId)).thenReturn(Optional.of(session));
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(moneyTypeRepository.findAllByOrderByDenominationDesc()).thenReturn(getMockedMoneyTypes());

        Map<Integer, Integer> changeMap = new HashMap<>();
        changeMap.put(1, 50); 
        PurchaseResult result = productService.purchaseProduct(sessionId, productId);
        
        assertNotNull(result);
        assertEquals(changeMap, result.getChangeMap());  
        verify(sessionRepository).save(session);
        verify(productRepository).save(product);
    }

    private List<MoneyType> getMockedMoneyTypes() {
        return List.of(
            new MoneyType(1, 100), // 100 units of $1
            new MoneyType(5, 50)  // 50 units of $5
        );
    }
}
