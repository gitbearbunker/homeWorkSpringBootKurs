package ru.netology.moneytransferservice.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.netology.moneytransferservice.model.*;

import java.math.BigDecimal;

@SpringBootTest
class TransferRepositoryTest {

    @Autowired
    TransferRepository transferRepository;

    TransferRequest transferRequest = new TransferRequest(
            1111111111111111L,
            "1234",
            123,
            2222222222222222L,
            new Amount(new BigDecimal("500"))
    );
    String operationId = "1";

    @Test
    void addTransferTest() {
        transferRepository.addTransfer(operationId, transferRequest);

        TransferRequest actual = transferRepository.getOperationIdsRepository().get("1");
        TransferRequest expected = transferRequest;

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void generateOperationIdTest() {
        String actual = transferRepository.generateOperationId();
        String expected = "1";
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void findCardNumberTest() {
        boolean actual = transferRepository.findCardNumber(transferRequest.getFromCardNumber());
        boolean expected = true;
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void updateBalance() {
        transferRepository.updateBalance(transferRequest.getFromCardNumber(), new BigDecimal("9500"));

        Amount actual = transferRepository.getCardRepository().get(transferRequest.getFromCardNumber()).getAmount();
        Amount expected = new Amount(new BigDecimal("9500"));
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void cvcCheckTest() {
        int actual = transferRepository.getCardRepository().get(transferRequest.getFromCardNumber()).getCardCvc();
        int expected = 123;
        Assertions.assertEquals(expected, actual);
    }
}