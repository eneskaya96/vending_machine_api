package com.vendingmachine.service;

import com.vendingmachine.domain.TransactionSession;
import com.vendingmachine.repository.TransactionSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class TransactionSessionService {

    private final TransactionSessionRepository sessionRepository;

    @Autowired
    public TransactionSessionService(TransactionSessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    public TransactionSession startSession() {
        TransactionSession session = new TransactionSession();
        session.setMoneyInserted(new HashMap<>());
        session.setTotalAmount(0);
        return sessionRepository.save(session);
    }

    public TransactionSession addMoney(TransactionSession session, Integer denomination, Integer quantity) {
        if (session != null) {
            Map<Integer, Integer> moneyInserted = session.getMoneyInserted();
            moneyInserted.merge(denomination, quantity, Integer::sum);
            session.setTotalAmount(session.getTotalAmount() + denomination * quantity);
            return sessionRepository.save(session);
        } else {
            throw new IllegalArgumentException("Session not found");
        }
    }

    public TransactionSession refundMoney(TransactionSession session) {
        if (session != null) {
            session.getMoneyInserted().clear();
            session.setTotalAmount(0);
            return sessionRepository.save(session);
        } else {
            throw new IllegalArgumentException("Session not found");
        }
    }

    public Optional<TransactionSession> findById(Long id) {
        return sessionRepository.findById(id);
    }

    public int getTotalAmount(Long sessionId) {
        Optional<TransactionSession> session = findById(sessionId);
        return session.map(TransactionSession::getTotalAmount).orElse(0);
    }
    
    public void resetSession(Long sessionId) {
        TransactionSession session = sessionRepository.findById(sessionId)
            .orElseThrow(() -> new IllegalStateException("Session not found with ID: " + sessionId));

        session.setTotalAmount(0);
        session.setMoneyInserted(new HashMap<>());
        sessionRepository.save(session);
        
    }
}
