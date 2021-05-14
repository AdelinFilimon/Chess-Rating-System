package com.gmail.filimon24.adelin.chessratingsystem.business.validation;

import com.gmail.filimon24.adelin.chessratingsystem.Constants;
import org.springframework.stereotype.Component;

@Component
public class EmailValidator implements DefaultValidator<String> {
    @Override
    public Boolean isValid(String s) {
        return s.matches(Constants.EMAIL_REGEX);
    }
}
