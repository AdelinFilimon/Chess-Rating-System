package com.gmail.filimon24.adelin.chessratingsystem.business.exception;

public class UserAlreadyExistsException extends Exception {
    public UserAlreadyExistsException(AttributeType attributeType, String attribute) {
        super(String.format("User with %s \"%s\" already exists", attributeType.name().toLowerCase(), attribute));
    }
}
