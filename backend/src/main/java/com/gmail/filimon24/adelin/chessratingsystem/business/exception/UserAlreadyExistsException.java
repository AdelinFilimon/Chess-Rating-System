package com.gmail.filimon24.adelin.chessratingsystem.business.exception;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(UserField userField, String attribute) {
        super(String.format("User with %s \"%s\" already exists", userField.name(), attribute));
    }
}
