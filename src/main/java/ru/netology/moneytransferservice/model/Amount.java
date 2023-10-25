package ru.netology.moneytransferservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class Amount {
    BigDecimal amount;
}