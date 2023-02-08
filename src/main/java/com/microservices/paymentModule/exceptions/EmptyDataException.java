package com.microservices.paymentModule.exceptions;

public class EmptyDataException extends Exception {
    public EmptyDataException(String message) {
        super(message);
    }
}
