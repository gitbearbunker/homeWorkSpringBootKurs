package ru.netology.moneytransferservice.exception;

public class CvcNotFoundException extends RuntimeException {
    public CvcNotFoundException(String msg) {
        super(msg);
    }
}