package com.gmail.filimon24.adelin.chessratingsystem.business.exception;

public class UnauthorizedRequestException extends RuntimeException {
    public UnauthorizedRequestException(String message) {
        super(message);
    }
}
