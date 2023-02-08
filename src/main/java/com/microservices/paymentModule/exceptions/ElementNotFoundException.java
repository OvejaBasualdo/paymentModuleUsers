package com.microservices.paymentModule.exceptions;

public class ElementNotFoundException extends Exception {
    public ElementNotFoundException(String message) {
        super(message);
    }
}