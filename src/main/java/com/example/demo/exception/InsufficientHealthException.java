package com.example.demo.exception;

public class InsufficientHealthException extends RuntimeException {
    public InsufficientHealthException(String message) {
        super(message);
    }
}
