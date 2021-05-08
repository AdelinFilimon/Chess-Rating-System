package com.gmail.filimon24.adelin.chessratingsystem.persistence.entity;

import com.gmail.filimon24.adelin.chessratingsystem.Constants;
import com.gmail.filimon24.adelin.chessratingsystem.persistence.util.Country;
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

    @Column(nullable = false, length = Constants.USER_ATTRIBUTE_LENGTH)
    private String username;

    @Column(nullable = false, length = Constants.USER_ATTRIBUTE_LENGTH)
    private String firstName;

    @Column(nullable = false, length = Constants.USER_ATTRIBUTE_LENGTH)
    private String lastName;

    @Column(nullable = false, length = Constants.USER_PASSWORD_LENGTH)
    private String password;

    @Column(nullable = false, length = Constants.USER_ATTRIBUTE_LENGTH)
    private String email;

    @Column
    @DateTimeFormat(pattern = Constants.DATETIME_FORMAT)
    private DateTime birthday;

    @Column(length = Constants.COUNTRY_FIELD_LENGTH)
    private Country country;

    @Column
    private Integer rating;

    @Column
    private Integer nrOfWins;

    @Column
    private Integer nrOfDraws;

    @Column
    private Integer nrOfLosses;

    @Column(length = Constants.USER_STATUS_LENGTH)
    private String status;

    @Column(nullable = false)
    private Boolean isAdministrator;

}
