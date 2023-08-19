package com.example.locallibrary1.error;

public class CustomerAlreadyExistException extends Exception {

    public CustomerAlreadyExistException(String message) {
        super(message);
    }

    public CustomerAlreadyExistException(String message, Throwable cause) {
        super(message, cause);
    }
}