package com.gmail.filimon24.adelin.chessratingsystem.business.validation;

import com.gmail.filimon24.adelin.chessratingsystem.business.exception.AttributeType;

import java.util.List;

public interface ModelValidator<T> extends DefaultValidator<T> {
    List<AttributeType> getInvalidAttributes(T t);
}
