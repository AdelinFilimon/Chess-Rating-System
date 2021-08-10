package com.gmail.filimon24.adelin.chessratingsystem.business.exception;

public class InvalidUserFieldException extends RuntimeException {
    public InvalidUserFieldException(UserField userField) {
        super(String.format("%s is not valid", userField.name()));
    }
}
