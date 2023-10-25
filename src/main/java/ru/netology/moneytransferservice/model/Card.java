package ru.netology.moneytransferservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Card {
    private long cardNumber;
    private String cardValidity;
    private int cardCvc;
    Amount amount;
}