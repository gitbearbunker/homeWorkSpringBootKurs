package ru.netology.moneytransferservice.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ConfirmOperation {

    private String operationId;

    @NotNull
    @Min(4)
    private String code;
}