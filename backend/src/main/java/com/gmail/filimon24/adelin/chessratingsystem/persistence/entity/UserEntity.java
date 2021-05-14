package com.gmail.filimon24.adelin.chessratingsystem.persistence.entity;

import com.gmail.filimon24.adelin.chessratingsystem.Constants;
import com.gmail.filimon24.adelin.chessratingsystem.persistence.util.Country;
import lombok.*;
import org.joda.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = Constants.USER_ATTRIBUTE_LENGTH)
    private String username;

    @Column(nullable = false, length = Constants.USER_ATTRIBUTE_LENGTH)
    private String firstName;

    @Column(nullable = false, length = Constants.USER_ATTRIBUTE_LENGTH)
    private String lastName;

    @Column(nullable = false, length = Constants.USER_PASSWORD_LENGTH)
    private String password;

    @Column(unique = true, nullable = false, length = Constants.USER_ATTRIBUTE_LENGTH)
    private String email;

    @Column
    @DateTimeFormat(pattern = Constants.BIRTHDAY_FORMAT)
    private LocalDate birthday;

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

    @ToString.Exclude
    @OneToMany(mappedBy = "whitePiecesPlayer")
    private List<GameHistoryEntity> whitePiecesGames;

    @ToString.Exclude
    @OneToMany(mappedBy = "blackPiecesPlayer")
    private List<GameHistoryEntity> blackPiecesGames;

    @ToString.Exclude
    @OneToMany(mappedBy = "winner")
    private List<GameHistoryEntity> winningGames;

}
