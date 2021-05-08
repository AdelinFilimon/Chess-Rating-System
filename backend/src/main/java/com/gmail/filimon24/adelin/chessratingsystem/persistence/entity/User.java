package com.gmail.filimon24.adelin.chessratingsystem.persistence.entity;

import com.gmail.filimon24.adelin.chessratingsystem.Constants;
import lombok.Data;
import org.joda.time.DateTime;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;

@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = Constants.userAttributesLength)
    private String username;

    @Column(nullable = false, length = Constants.userAttributesLength)
    private String firstName;

    @Column(nullable = false, length = Constants.userAttributesLength)
    private String lastName;

    @Column(nullable = false, length = Constants.passwordLength)
    private String password;

    @Column(nullable = false, length = Constants.userAttributesLength)
    private String email;

    @Column(nullable = false)
    @DateTimeFormat(pattern = Constants.datetimeFormat)
    private DateTime birthday;

    @Column(nullable = false, length = Constants.countryFieldLength)
    @Enumerated(EnumType.STRING)
    private Country country;

    @Column(nullable = false)
    private Integer rating;

    @Column(nullable = false)
    private Integer nrOfWins;

    @Column(nullable = false)
    private Integer nrOfDraws;

    @Column(nullable = false)
    private Integer nrOfLosses;

    @Column(length = Constants.statusLength)
    private String status;

    @Column
    private Boolean isAdministrator;

}
