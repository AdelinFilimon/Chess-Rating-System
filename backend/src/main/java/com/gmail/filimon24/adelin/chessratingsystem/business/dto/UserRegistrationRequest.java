package com.gmail.filimon24.adelin.chessratingsystem.business.dto;

import lombok.Data;

@Data
public class UserRegistrationRequest {
    private String username;
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private String dateOfBirth;
    private String country;
    private String status;
}
