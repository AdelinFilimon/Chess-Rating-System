package com.gmail.filimon24.adelin.chessratingsystem.business.validation;

import com.gmail.filimon24.adelin.chessratingsystem.business.exception.AttributeType;
import com.gmail.filimon24.adelin.chessratingsystem.business.model.User;
import com.gmail.filimon24.adelin.chessratingsystem.persistence.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class UserValidator implements ModelValidator<User> {

    private final UsernameValidator usernameValidator;
    private final EmailValidator emailValidator;
    private final PasswordValidator passwordValidator;

    private final UserRepository userRepository;

    @Override
    public Boolean isValid(User user) {
        return usernameValidator.isValid(user.getUsername()) && emailValidator.isValid(user.getEmail())
                                    && passwordValidator.isValid(user.getPassword());
    }

    @Override
    public List<AttributeType> getInvalidAttributes(User user) {
        List<AttributeType> invalidAttributes = new ArrayList<>();

        if (!usernameValidator.isValid(user.getUsername())) invalidAttributes.add(AttributeType.USERNAME);
        if (!emailValidator.isValid(user.getEmail())) invalidAttributes.add(AttributeType.EMAIL);
        if (!passwordValidator.isValid(user.getPassword())) invalidAttributes.add(AttributeType.PASSWORD);

        return invalidAttributes;
    }

    public Boolean isEmailAlreadyUsed(String email) {
        return userRepository.existsByEmail(email);
    }

    public Boolean isUsernameAlreadyUsed(String username) {
        return userRepository.existsByUsername(username);
    }
}
