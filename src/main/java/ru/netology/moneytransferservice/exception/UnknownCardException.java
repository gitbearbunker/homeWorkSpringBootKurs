package ru.netology.moneytransferservice.exception;

public class UnknownCardException extends RuntimeException {
    public UnknownCardException(String msg) {
        super(msg);
    }
}