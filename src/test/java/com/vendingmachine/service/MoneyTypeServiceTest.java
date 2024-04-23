package com.vendingmachine.service;

import com.vendingmachine.domain.MoneyType;
import com.vendingmachine.repository.MoneyTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class MoneyTypeServiceTest {

    @Mock
    private MoneyTypeRepository moneyTypeRepository;

    @InjectMocks
    private MoneyTypeService moneyTypeService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveMoneyType() {
        MoneyType moneyType = new MoneyType(); // Assume this is your domain class with appropriate fields
        when(moneyTypeRepository.save(any(MoneyType.class))).thenReturn(moneyType);

        MoneyType created = moneyTypeService.saveMoneyType(moneyType);
        assertNotNull(created);
        verify(moneyTypeRepository).save(moneyType);
    }

    @Test
    public void testFindAllMoneyTypes() {
        when(moneyTypeRepository.findAll()).thenReturn(Arrays.asList(new MoneyType(), new MoneyType()));

        List<MoneyType> moneyTypes = moneyTypeService.findAllMoneyTypes();
        assertNotNull(moneyTypes);
        assertEquals(2, moneyTypes.size());
        verify(moneyTypeRepository).findAll();
    }

    @Test
    public void testUpdateMoneyTypeQuantity() {
        MoneyType moneyType = new MoneyType(); // Assume this has a setQuantity method
        Long moneyTypeId = 1L;
        when(moneyTypeRepository.findById(moneyTypeId)).thenReturn(Optional.of(moneyType));
        when(moneyTypeRepository.save(any(MoneyType.class))).thenReturn(moneyType);

        MoneyType updated = moneyTypeService.updateMoneyTypeQuantity(moneyTypeId, 10);
        assertNotNull(updated);
        assertEquals(10, updated.getQuantity());
        verify(moneyTypeRepository).save(moneyType);
    }

    @Test
    public void testUpdateMoneyTypeQuantity_NotFound() {
        Long moneyTypeId = 1L;
        when(moneyTypeRepository.findById(moneyTypeId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            moneyTypeService.updateMoneyTypeQuantity(moneyTypeId, 10);
        });

        String expectedMessage = "MoneyType not found";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testDeleteMoneyType() {
        Long moneyTypeId = 1L;
        doNothing().when(moneyTypeRepository).deleteById(moneyTypeId);

        moneyTypeService.deleteMoneyType(moneyTypeId);
        verify(moneyTypeRepository).deleteById(moneyTypeId);
    }

    @Test
    public void testUpdateQuantity_Valid() {
        MoneyType moneyType = new MoneyType(); // Assume this has a setQuantity method
        Long moneyTypeId = 1L;
        when(moneyTypeRepository.findById(moneyTypeId)).thenReturn(Optional.of(moneyType));
        when(moneyTypeRepository.save(any(MoneyType.class))).thenReturn(moneyType);

        MoneyType updated = moneyTypeService.updateQuantity(moneyTypeId, 5);
        assertNotNull(updated);
        assertEquals(5, updated.getQuantity());
        verify(moneyTypeRepository).save(moneyType);
    }

    @Test
    public void testUpdateQuantity_InvalidQuantity() {
        Long moneyTypeId = 1L;
        MoneyType moneyType = new MoneyType();
        when(moneyTypeRepository.findById(moneyTypeId)).thenReturn(Optional.of(moneyType));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            moneyTypeService.updateQuantity(moneyTypeId, -1);
        });

        String expectedMessage = "Quantity cannot be negative";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}
