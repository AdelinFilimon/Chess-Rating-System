package com.gmail.filimon24.adelin.chessratingsystem.business.service;

import com.gmail.filimon24.adelin.chessratingsystem.business.dto.GameInvitationRequest;
import com.gmail.filimon24.adelin.chessratingsystem.business.dto.UserProfileResponse;
import com.gmail.filimon24.adelin.chessratingsystem.business.dto.UserRegistrationRequest;
import com.gmail.filimon24.adelin.chessratingsystem.business.exception.*;
import com.gmail.filimon24.adelin.chessratingsystem.business.validation.EmailValidator;
import com.gmail.filimon24.adelin.chessratingsystem.business.validation.PasswordValidator;
import com.gmail.filimon24.adelin.chessratingsystem.business.validation.UserValidator;
import com.gmail.filimon24.adelin.chessratingsystem.business.model.User;
import com.gmail.filimon24.adelin.chessratingsystem.business.validation.UsernameValidator;
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
    private final UsernameValidator usernameValidator;
    private final PasswordValidator passwordValidator;
    private final EmailValidator emailValidator;

    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public User insertUser(User user) {
        validateUser(user);

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

    public User getUser(Long id) {
        UserEntity userEntity = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        return modelMapper.map(userEntity, User.class);
    }

    public User updateUser(Long id, User user) {

        UserEntity userToBeUpdated = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        UserEntity userFromWhichToUpdate = modelMapper.map(user, UserEntity.class);
        updateUserFields(userToBeUpdated, userFromWhichToUpdate);
        return modelMapper.map(userRepository.save(userToBeUpdated), User.class);
    }

    public void deleteUser(Long id) {
        UserEntity userEntity = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        if (!userEntity.getIsAdministrator()) {
            userRepository.deleteById(id);
        } else throw new UnauthorizedRequestException("Unauthorized to delete admin");
    }

    private void validateUser(User user) {
        List<UserField> invalidAttributes = userValidator.getInvalidAttributes(user);
        if (!invalidAttributes.isEmpty()) throw new InvalidUserFieldException(invalidAttributes.get(0));

        if (userValidator.isUsernameAlreadyUsed(user.getUsername()))
            throw new UserAlreadyExistsException(UserField.USERNAME, user.getUsername());

        if (userValidator.isEmailAlreadyUsed(user.getEmail()))
            throw new UserAlreadyExistsException(UserField.EMAIL, user.getEmail());
    }

    private void updateUserFields(UserEntity userToBeUpdated, UserEntity userFromWhichToUpdate) {
        updateUserIdentificationInfo(userToBeUpdated, userFromWhichToUpdate);
        updateUserSensitiveInfo(userToBeUpdated, userFromWhichToUpdate);
        updateUserRatingInfo(userToBeUpdated, userFromWhichToUpdate);
    }

    private void updateUserRatingInfo(UserEntity userToBeUpdated, UserEntity userFromWhichToUpdate) {
        if (userFromWhichToUpdate.getRating() != null) {
            userToBeUpdated.setRating(userFromWhichToUpdate.getRating());
        }

        if (userFromWhichToUpdate.getNrOfWins() != null) {
            userToBeUpdated.setNrOfWins(userFromWhichToUpdate.getNrOfWins());
        }

        if (userFromWhichToUpdate.getNrOfDraws() != null) {
            userToBeUpdated.setNrOfDraws(userFromWhichToUpdate.getNrOfDraws());
        }

        if (userFromWhichToUpdate.getNrOfLosses() != null) {
            userToBeUpdated.setNrOfLosses(userFromWhichToUpdate.getNrOfLosses());
        }
    }

    private void updateUserSensitiveInfo(UserEntity userToBeUpdated, UserEntity userFromWhichToUpdate) {
        if (userFromWhichToUpdate.getPassword() != null) {
            if (!passwordValidator.isValid(userFromWhichToUpdate.getPassword()))
                throw new InvalidUserFieldException(UserField.PASSWORD);
            userToBeUpdated.setPassword(passwordEncoder.encode(userFromWhichToUpdate.getPassword()));
        }

        if (userFromWhichToUpdate.getDateOfBirth() != null) {
            userToBeUpdated.setDateOfBirth(userFromWhichToUpdate.getDateOfBirth());
        }

        if (userFromWhichToUpdate.getCountry() != null) {
            userToBeUpdated.setCountry(userFromWhichToUpdate.getCountry());
        }

        if (userFromWhichToUpdate.getStatus() != null) {
            userToBeUpdated.setStatus(userFromWhichToUpdate.getStatus());
        }

        if (userFromWhichToUpdate.getIsAdministrator() != null) {
            userToBeUpdated.setIsAdministrator(userFromWhichToUpdate.getIsAdministrator());
        }
    }

    private void updateUserIdentificationInfo(UserEntity userToBeUpdated, UserEntity userFromWhichToUpdate) {
        if (userFromWhichToUpdate.getUsername() != null && !userValidator.isUsernameAlreadyUsed(userFromWhichToUpdate.getUsername())) {
            if (!usernameValidator.isValid(userFromWhichToUpdate.getUsername()))
                throw new InvalidUserFieldException(UserField.USERNAME);
            userToBeUpdated.setUsername(userFromWhichToUpdate.getUsername());
        }

        if (userFromWhichToUpdate.getFirstName() != null) {
            userToBeUpdated.setFirstName(userFromWhichToUpdate.getFirstName());
        }

        if (userFromWhichToUpdate.getLastName() != null) {
            userToBeUpdated.setLastName(userFromWhichToUpdate.getLastName());
        }

        if (userFromWhichToUpdate.getEmail() != null && !userValidator.isEmailAlreadyUsed(userFromWhichToUpdate.getEmail())) {
            if (!emailValidator.isValid(userFromWhichToUpdate.getEmail()))
                throw new InvalidUserFieldException(UserField.EMAIL);
            userToBeUpdated.setEmail(userFromWhichToUpdate.getEmail());
        }
    }

    public User registerUser(UserRegistrationRequest userRegistrationRequest) {
        User user = modelMapper.map(userRegistrationRequest, User.class);
        validateUser(user);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRating(1200);
        user.setNrOfWins(0);
        user.setNrOfDraws(0);
        user.setNrOfLosses(0);
        user.setIsAdministrator(false);

        return modelMapper.map(userRepository.save(modelMapper.map(user, UserEntity.class)), User.class);
    }

    public User findUserByGameInvitationRequest(GameInvitationRequest gameInvitationRequest) {
        String username = gameInvitationRequest.getUsername();
        String email = gameInvitationRequest.getEmail();

        if (username != null) {
            return modelMapper.map(
                    userRepository.findByUsername(username)
                            .orElseThrow(() -> new UserNotFoundException(UserField.USERNAME, username)),
                    User.class);
        }

        if (email != null) {
            return modelMapper.map(
                    userRepository.findByEmail(email)
                            .orElseThrow(() -> new UserNotFoundException(UserField.EMAIL, email)),
                    User.class);
        }

        throw new UserNotFoundException();
    }

    public User findUserByUsername(String username) {
        return modelMapper.map(
                userRepository.findByUsername(username)
                        .orElseThrow(() -> new UserNotFoundException(UserField.USERNAME, username)),
                User.class);
    }

    public UserProfileResponse getProfileByUsername(String username) {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(UserNotFoundException::new);

        return modelMapper.map(modelMapper.map(user, User.class), UserProfileResponse.class);
    }
}
