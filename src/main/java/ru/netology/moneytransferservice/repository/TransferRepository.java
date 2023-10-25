package ru.netology.moneytransferservice.repository;

import org.springframework.stereotype.Repository;
import ru.netology.moneytransferservice.model.*;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class TransferRepository {
    private Card card1 = new Card(1111111111111111L, "1234", 123, new Amount(new BigDecimal("10000")));
    private Card card2 = new Card(2222222222222222L, "1234", 234, new Amount(new BigDecimal("500")));
    private Card card3 = new Card(3333333333333333L, "1234", 345, new Amount(new BigDecimal("200")));

    private final ConcurrentHashMap<Long, Card> cardRepository = new ConcurrentHashMap<>(Map.of(
            card1.getCardNumber(), card1,
            card2.getCardNumber(), card2,
            card3.getCardNumber(), card3
    ));

    private AtomicInteger ids = new AtomicInteger();

    private final ConcurrentHashMap<String, TransferRequest> operationIdsRepository = new ConcurrentHashMap<>();

    public void addTransfer(String operationId, TransferRequest transferRequest) {
        operationIdsRepository.put(operationId, transferRequest);
    }

    public String generateOperationId() {
        Integer id = ids.incrementAndGet();
        return id.toString();
    }

    public boolean findCardNumber(long cardNumber) {
        if (!cardRepository.containsKey(cardNumber)) {
            return false;
        }
        return true;
    }

    public int cvcCheck(long cardNumber) {
        return cardRepository.get(cardNumber).getCardCvc();
    }

    public BigDecimal amountCheck(long cardNumber) {
        return cardRepository.get(cardNumber).getAmount().getAmount();
    }

    public void updateBalance(long cardNumber, BigDecimal amount) {
        cardRepository.get(cardNumber).getAmount().setAmount(amount);
    }

    public AtomicInteger getIds() {
        return ids;
    }

    public ConcurrentHashMap<String, TransferRequest> getOperationIdsRepository() {
        return operationIdsRepository;
    }

    public ConcurrentHashMap<Long, Card> getCardRepository() {
        return cardRepository;
    }
}