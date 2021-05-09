package com.gmail.filimon24.adelin.chessratingsystem.persistence.entity;

import com.gmail.filimon24.adelin.chessratingsystem.Constants;
import lombok.Data;
import org.joda.time.DateTime;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;

@Entity
@Data
public class GameHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn
    @ManyToOne
    private User whitePiecesPlayer;

    @JoinColumn
    @ManyToOne
    private User blackPiecesPlayer;

    @JoinColumn
    @ManyToOne
    private User winner;

    @Column
    @DateTimeFormat(pattern = Constants.DATETIME_FORMAT)
    private DateTime startingDate;

    @Column
    private Integer durationInSeconds;

}
