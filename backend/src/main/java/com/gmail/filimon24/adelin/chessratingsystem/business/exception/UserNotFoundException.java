package com.gmail.filimon24.adelin.chessratingsystem.business.exception;

public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException() {
        super();
    }

    public UserNotFoundException(UserField userField, String field) {
        super("User with " + userField.name() + " " + field + " not found");
    }

    public UserNotFoundException(Long id) {
        this(UserField.ID, id.toString());
    }
}
