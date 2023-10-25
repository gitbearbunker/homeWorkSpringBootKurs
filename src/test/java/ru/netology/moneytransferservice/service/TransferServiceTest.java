package ru.netology.moneytransferservice.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.netology.moneytransferservice.model.*;
import ru.netology.moneytransferservice.repository.TransferRepository;

import java.math.BigDecimal;
import java.text.ParseException;

import static org.mockito.Mockito.when;

@SpringBootTest
class TransferServiceTest {

    @MockBean
    private TransferRepository transferRepository;

    @Autowired
    TransferService transferService;

    @Test
    void transferTest() throws ParseException {
        TransferRequest transferRequest = new TransferRequest(
                1111111111111111L,
                "1234",
                123,
                2222222222222222L,
                new Amount(new BigDecimal("500"))
        );

        when(transferRepository.findCardNumber(transferRequest.getFromCardNumber())).thenReturn(true);
        when(transferRepository.findCardNumber(transferRequest.getToCardNumber())).thenReturn(true);
        when(transferRepository.cvcCheck(transferRequest.getFromCardNumber())).thenReturn(123);
        when(transferRepository.amountCheck(transferRequest.getFromCardNumber())).thenReturn(new BigDecimal("10000"));
        when(transferRepository.amountCheck(transferRequest.getToCardNumber())).thenReturn(new BigDecimal("500"));
        when(transferRepository.generateOperationId()).thenReturn("1");

        ResponseEntity<String> actual = transferService.transfer(transferRequest);
        ResponseEntity<String> expected = new ResponseEntity("Присвоен id 1. Ожидается подтверждение", HttpStatus.OK);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void confirmTest() {
        ConfirmOperation confirmOperation = new ConfirmOperation(
                "1",
                "0000"
        );

        ResponseEntity<String> actual = transferService.confirm(confirmOperation);
        ResponseEntity<String> expected = new ResponseEntity<>("Операция с id 1 подтверждена", HttpStatus.OK);

        Assertions.assertEquals(expected, actual);
    }
}