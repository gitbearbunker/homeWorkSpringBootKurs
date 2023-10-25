package ru.netology.moneytransferservice.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.netology.moneytransferservice.model.*;
import ru.netology.moneytransferservice.service.TransferService;

import java.text.ParseException;

@RestController
@CrossOrigin
@AllArgsConstructor
public class TransferController {

    private final TransferService transferService;

    @PostMapping("/transfer")
    public ResponseEntity<String> transferResponse(@RequestBody @Validated TransferRequest transferRequest) throws ParseException {
        return transferService.transfer(transferRequest);
    }

    @PostMapping("/confirmOperation")
    public ResponseEntity<String> confirmOperation(@RequestBody @Validated ConfirmOperation confirmOperation) {
        return transferService.confirm(confirmOperation);
    }
}