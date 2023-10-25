package ru.netology.moneytransferservice.exception;

public class ExpiredCardException extends RuntimeException {
    public ExpiredCardException(String msg) {
        super(msg);
    }
}