package com.eurder.service;

public class NotEverythingFilledInExeption extends RuntimeException {


    public NotEverythingFilledInExeption(String message) {
        super(message);
    }

}
