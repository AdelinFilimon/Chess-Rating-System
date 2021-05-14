package com.gmail.filimon24.adelin.chessratingsystem.persistence.entity;

import com.gmail.filimon24.adelin.chessratingsystem.Constants;
import lombok.Data;
import org.joda.time.DateTime;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;

@Entity
@Data
@Table(name = "game_history")
public class GameHistoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(nullable = false)
    @ManyToOne
    private UserEntity whitePiecesPlayer;

    @JoinColumn(nullable = false)
    @ManyToOne
    private UserEntity blackPiecesPlayer;

    @JoinColumn(nullable = false)
    @ManyToOne
    private UserEntity winner;

    @Column(nullable = false)
    @DateTimeFormat(pattern = Constants.DATETIME_FORMAT)
    private DateTime startingDate;

    @Column(nullable = false)
    private Integer durationInSeconds;

}
