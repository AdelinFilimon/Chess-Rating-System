package com.gmail.filimon24.adelin.chessratingsystem.business.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private String birthday;
    private String country;
    private String status;
    private Integer rating;
    private Integer nrOfWins;
    private Integer nrOfDraws;
    private Integer nrOfLosses;
    private Boolean isAdministrator;
}
