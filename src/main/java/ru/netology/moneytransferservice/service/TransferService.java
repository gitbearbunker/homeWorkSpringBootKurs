package ru.netology.moneytransferservice.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.netology.moneytransferservice.exception.*;
import ru.netology.moneytransferservice.model.*;
import ru.netology.moneytransferservice.repository.TransferRepository;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@AllArgsConstructor
@Slf4j
public class TransferService {

    private final TransferRepository transferRepository;

    public ResponseEntity<String> transfer(TransferRequest transferRequest) throws ParseException {

        long fromCardNumber = transferRequest.getFromCardNumber();
        if (!cardNumberCheck(fromCardNumber)) {
            throw new UnknownCardException("Неизвестный номер карты " + fromCardNumber);
        }

        String validity = transferRequest.getFromCardValidity();
        if (!validityCheck(validity)) {
            throw new ExpiredCardException("Срок действия карты истек " + validity + ", либо введены неверные данные");
        }

        int fromCardCvc = transferRequest.getFromCardCvc();
        if (!cvcCheck(fromCardNumber, fromCardCvc)) {
            throw new CvcNotFoundException("Неверное введен CVC");
        }

        BigDecimal amount = transferRequest.getAmount().getAmount();
        if (!amountCheck(fromCardNumber, amount)) {
            throw new NotEnoughMoneyException("На карте недостаточно средств");
        }

        long toCardNumber = transferRequest.getToCardNumber();
        if (!cardNumberCheck(toCardNumber)) {
            throw new UnknownCardException("Неизвестный номер карты " + toCardNumber);
        }

        updateBalance(fromCardNumber, toCardNumber, amount);

        String operationId = transferRepository.generateOperationId();
        transferRepository.addTransfer(operationId, transferRequest);

        log.info("Запрос на перевод: с карты {} на карту {}, сумма {}, присвоен id {}. Ожидается подтверждение", fromCardNumber, toCardNumber, amount, operationId);
        return new ResponseEntity("Присвоен id " + operationId + ". Ожидается подтверждение", HttpStatus.OK);
    }

    public ResponseEntity<String> confirm(ConfirmOperation confirmOperation) {
        String operationId = confirmOperation.getOperationId();
        String code = confirmOperation.getCode();

        if (code.equals("0000")) {
            log.info("Операция с id {} подтверждена", operationId);
            return new ResponseEntity("Операция с id " + operationId + " подтверждена", HttpStatus.OK);
        } else {
            throw new IncorrectСodeException("Введенный код не совпадает");
        }
    }

    private void updateBalance(long fromCardNumber, long toCardNumber, BigDecimal amount) {
        BigDecimal fromCardAmount = transferRepository.amountCheck(fromCardNumber);
        BigDecimal toCardAmount = transferRepository.amountCheck(toCardNumber);

        BigDecimal updateFromBalance = fromCardAmount.subtract(amount);
        BigDecimal updateToBalance = toCardAmount.add(amount);

        transferRepository.updateBalance(fromCardNumber, updateFromBalance);
        transferRepository.updateBalance(toCardNumber, updateToBalance);
    }

    private boolean amountCheck(long cardNumber, BigDecimal amount) {
        BigDecimal cardAmount = transferRepository.amountCheck(cardNumber);

        if (amount.compareTo(cardAmount) > 0) {
            return false;
        }

        return true;
    }

    private boolean validityCheck(String validity) throws ParseException {
        int month = Integer.parseInt(validity.substring(0, 2));

        if (month < 1 || month > 12) {
            return false;
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMyy");
        simpleDateFormat.setLenient(false);
        Date expiry = simpleDateFormat.parse(validity);

        return expiry.after(new Date());
    }

    private boolean cvcCheck(long cardNumber, int fromCardCvc) {
        int cardCvc = transferRepository.cvcCheck(cardNumber);

        if (cardCvc != fromCardCvc) {
            return false;
        }
        return true;
    }

    private boolean cardNumberCheck(long cardNumber) {
        return transferRepository.findCardNumber(cardNumber);
    }
}