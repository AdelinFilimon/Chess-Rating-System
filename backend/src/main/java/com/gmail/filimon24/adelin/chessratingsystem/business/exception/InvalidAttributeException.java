package com.gmail.filimon24.adelin.chessratingsystem.business.exception;

public class InvalidAttributeException extends Exception {
    public InvalidAttributeException(AttributeType attributeType) {
        super(String.format("%s is not valid", attributeType.name().toLowerCase()));
    }
}
