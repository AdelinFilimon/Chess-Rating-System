package com.gmail.filimon24.adelin.chessratingsystem.business.service;

import com.gmail.filimon24.adelin.chessratingsystem.business.exception.AttributeType;
import com.gmail.filimon24.adelin.chessratingsystem.business.exception.InvalidAttributeException;
import com.gmail.filimon24.adelin.chessratingsystem.business.exception.UserAlreadyExistsException;
import com.gmail.filimon24.adelin.chessratingsystem.business.validation.UserValidator;
import com.gmail.filimon24.adelin.chessratingsystem.business.model.User;
import com.gmail.filimon24.adelin.chessratingsystem.persistence.UserRepository;
import com.gmail.filimon24.adelin.chessratingsystem.persistence.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserValidator userValidator;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public User createUser(User user) throws InvalidAttributeException, UserAlreadyExistsException {
        List<AttributeType> invalidAttributes = userValidator.getInvalidAttributes(user);

        if (!invalidAttributes.isEmpty()) throw new InvalidAttributeException(invalidAttributes.get(0));

        if (userValidator.isUsernameAlreadyUsed(user.getUsername()))
            throw new UserAlreadyExistsException(AttributeType.USERNAME, user.getUsername());

        if (userValidator.isEmailAlreadyUsed(user.getEmail()))
            throw new UserAlreadyExistsException(AttributeType.EMAIL, user.getEmail());

        if (user.getIsAdministrator() == null) user.setIsAdministrator(false);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        UserEntity userEntity = modelMapper.map(user, UserEntity.class);

        return modelMapper.map(userRepository.save(userEntity), User.class);

    }

    public List<User> getUsers() {
        return userRepository.findAll().stream()
                .map(userEntity -> modelMapper.map(userEntity, User.class))
                .collect(Collectors.toList());
    }

}
