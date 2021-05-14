package com.gmail.filimon24.adelin.chessratingsystem;

import com.gmail.filimon24.adelin.chessratingsystem.persistence.UserRepository;
import com.gmail.filimon24.adelin.chessratingsystem.persistence.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@RequiredArgsConstructor
public class ChessRatingSystemApplication {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(ChessRatingSystemApplication.class, args);
    }

    @Bean
    public CommandLineRunner insertSuperAdmin() {
        return args -> {
            if (userRepository.existsByEmail(Constants.SUPER_ADMIN_EMAIL)) return;

            UserEntity user = UserEntity.builder()
                    .username(Constants.SUPER_ADMIN_USERNAME)
                    .password(passwordEncoder.encode(Constants.SUPER_ADMIN_PASSWORD))
                    .firstName(Constants.SUPER_ADMIN_FIRST_NAME)
                    .lastName(Constants.SUPER_ADMIN_LAST_NAME)
                    .email(Constants.SUPER_ADMIN_EMAIL)
                    .isAdministrator(true)
                    .build();

            userRepository.save(user);
        };
    }

}
