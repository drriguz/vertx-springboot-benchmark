package com.riguz.frameworks.model;

public class AppNotFoundException extends RuntimeException{
    public AppNotFoundException(String message) {
        super(message);
    }
}
