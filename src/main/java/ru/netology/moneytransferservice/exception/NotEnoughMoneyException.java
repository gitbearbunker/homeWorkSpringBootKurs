package ru.netology.moneytransferservice.exception;

public class NotEnoughMoneyException extends RuntimeException {
    public NotEnoughMoneyException(String msg) {
        super(msg);
    }
}