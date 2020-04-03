package com.eurder.api.controllers;

class NotCorrectUserException extends  RuntimeException {
    public NotCorrectUserException(String message) {
        super(message);
    }
}
