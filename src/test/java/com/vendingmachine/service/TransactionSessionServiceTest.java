package com.vendingmachine.service;

import com.vendingmachine.domain.TransactionSession;
import com.vendingmachine.repository.TransactionSessionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionSessionServiceTest {

    @Mock
    private TransactionSessionRepository sessionRepository;
    
    @InjectMocks
    private TransactionSessionService sessionService;

    @Test
    void testStartSession() {
        TransactionSession session = new TransactionSession();
        session.setMoneyInserted(new HashMap<>());
        session.setTotalAmount(0);
        when(sessionRepository.save(any(TransactionSession.class))).thenReturn(session);

        TransactionSession createdSession = sessionService.startSession();
        assertNotNull(createdSession);
        assertEquals(0, createdSession.getTotalAmount());
        verify(sessionRepository).save(any(TransactionSession.class));
    }

    @Test
    void testAddMoney() {
        TransactionSession session = new TransactionSession();
        session.setTotalAmount(0);
        session.setMoneyInserted(new HashMap<>());

        when(sessionRepository.save(any(TransactionSession.class))).thenReturn(session);

        TransactionSession updatedSession = sessionService.addMoney(session, 10, 5);
        assertEquals(50, updatedSession.getTotalAmount());
        assertTrue(updatedSession.getMoneyInserted().containsKey(10));
        assertEquals(5, updatedSession.getMoneyInserted().get(10));
        verify(sessionRepository).save(session);
    }

    @Test
    void testRefundMoney() {
        TransactionSession session = new TransactionSession();
        session.setTotalAmount(100);
        HashMap<Integer, Integer> moneyInserted = new HashMap<>();
        moneyInserted.put(10, 10);
        session.setMoneyInserted(moneyInserted);

        when(sessionRepository.save(any(TransactionSession.class))).thenReturn(session);

        TransactionSession refundedSession = sessionService.refundMoney(session);
        assertEquals(0, refundedSession.getTotalAmount());
        assertTrue(refundedSession.getMoneyInserted().isEmpty());
        verify(sessionRepository).save(session);
    }

    @Test
    void testFindById() {
        Long sessionId = 1L;
        TransactionSession session = new TransactionSession();
        when(sessionRepository.findById(sessionId)).thenReturn(Optional.of(session));

        Optional<TransactionSession> foundSession = sessionService.findById(sessionId);
        assertTrue(foundSession.isPresent());
        assertEquals(session, foundSession.get());
        verify(sessionRepository).findById(sessionId);
    }

    @Test
    void testGetTotalAmount() {
        Long sessionId = 1L;
        TransactionSession session = new TransactionSession();
        session.setTotalAmount(150);
        when(sessionRepository.findById(sessionId)).thenReturn(Optional.of(session));

        int totalAmount = sessionService.getTotalAmount(sessionId);
        assertEquals(150, totalAmount);
        verify(sessionRepository).findById(sessionId);
    }

    @Test
    void testResetSession() {
        Long sessionId = 1L;
        TransactionSession session = new TransactionSession();
        session.setTotalAmount(200);
        session.setMoneyInserted(new HashMap<>());
        when(sessionRepository.findById(sessionId)).thenReturn(Optional.of(session));
        when(sessionRepository.save(any(TransactionSession.class))).thenReturn(session);

        sessionService.resetSession(sessionId);
        assertEquals(0, session.getTotalAmount());
        assertTrue(session.getMoneyInserted().isEmpty());
        verify(sessionRepository).save(session);
        verify(sessionRepository).findById(sessionId);
    }
}
