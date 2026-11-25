package com.example.demo.exception;

public class InvalidDecisionException extends RuntimeException {
    public InvalidDecisionException(String message) {
        super(message);
    }
}
