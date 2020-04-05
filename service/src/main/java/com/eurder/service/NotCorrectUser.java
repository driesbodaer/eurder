package com.eurder.service;

public class NotCorrectUserException extends  RuntimeException {
    public NotCorrectUserException(String message) {
        super(message);
    }
}
