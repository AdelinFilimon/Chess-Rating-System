package com.gmail.filimon24.adelin.chessratingsystem;

import com.gmail.filimon24.adelin.chessratingsystem.business.model.User;
import com.gmail.filimon24.adelin.chessratingsystem.business.service.UserService;
import com.gmail.filimon24.adelin.chessratingsystem.persistence.UserRepository;
import com.gmail.filimon24.adelin.chessratingsystem.persistence.entity.UserEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServicesMockTest {

    @Mock
    private UserRepository userRepository;

    @Test
    public void whenSavedUserShouldReturnUser() {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("usernameA");
        userEntity.setFirstName("firstName");
        userEntity.setLastName("lastName");
        userEntity.setPassword("password123A");
        userEntity.setIsAdministrator(false);
        userEntity.setEmail("somemail@yahoo.com");

        User user = new User();
        user.setUsername("usernameA");
        user.setFirstName("firstName");
        user.setLastName("lastName");
        user.setPassword("password123A");
        user.setIsAdministrator(false);
        user.setEmail("somemail@yahoo.com");

        UserService userService = mock(UserService.class, Mockito.RETURNS_DEEP_STUBS);
        when(userRepository.save(ArgumentMatchers.any(UserEntity.class))).thenReturn(userEntity);

        User created = userService.insertUser(user);
        assertThat(created.getUsername()).isSameAs(user.getUsername());
        verify(userRepository.save(userEntity));
    }

    @Test
    public void whenGetAllShouldReturnListOfUsers() {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("usernameA");
        userEntity.setFirstName("firstName");
        userEntity.setLastName("lastName");
        userEntity.setPassword("password123A");
        userEntity.setIsAdministrator(false);
        userEntity.setEmail("somemail@yahoo.com");

        UserEntity userEntity2 = new UserEntity();
        userEntity2.setUsername("usernameB");
        userEntity2.setFirstName("firstName");
        userEntity2.setLastName("lastName");
        userEntity2.setPassword("password123A");
        userEntity2.setIsAdministrator(false);
        userEntity2.setEmail("somemail1@yahoo.com");

        UserService userService = mock(UserService.class, Mockito.RETURNS_DEEP_STUBS);
        when(userRepository.save(ArgumentMatchers.any(UserEntity.class))).thenReturn(userEntity);
        userRepository.save(userEntity);
        userRepository.save(userEntity2);

        assertThat(userService.getUsers()).isSameAs(Arrays.asList(userEntity, userEntity2));
        verify(userRepository.save(userEntity));
    }
}


