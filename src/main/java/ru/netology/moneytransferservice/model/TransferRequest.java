package ru.netology.moneytransferservice.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TransferRequest {

    @NotNull
    @Min(16)
    private long fromCardNumber;

    @NotNull
    @Min(4)
    private String fromCardValidity;

    @NotNull
    @Min(3)
    private int fromCardCvc;

    @NotNull
    @Min(16)
    private long toCardNumber;

    @Positive
    @NotNull
    Amount amount;
}