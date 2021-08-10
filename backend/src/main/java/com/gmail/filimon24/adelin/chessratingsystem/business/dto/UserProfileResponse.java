package com.gmail.filimon24.adelin.chessratingsystem.business.dto;

import lombok.Data;

@Data
public class UserProfileResponse {
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String dateOfBirth;
    private String country;
    private String status;

    private Integer rating;
    private Integer nrOfWins;
    private Integer nrOfLosses;
    private Integer nrOfDraws;
}
