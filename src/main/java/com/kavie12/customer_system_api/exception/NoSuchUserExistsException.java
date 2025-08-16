package com.kavie12.customer_system_api.exception;

public class NoSuchUserExistsException extends RuntimeException {
    public NoSuchUserExistsException(String message) {
        super(message);
    }
}
