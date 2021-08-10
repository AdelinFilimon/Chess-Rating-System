package com.gmail.filimon24.adelin.chessratingsystem.business.validation;

import com.gmail.filimon24.adelin.chessratingsystem.business.exception.UserField;

import java.util.List;

public interface ModelValidator<T> extends DefaultValidator<T> {
    List<UserField> getInvalidAttributes(T t);
}
