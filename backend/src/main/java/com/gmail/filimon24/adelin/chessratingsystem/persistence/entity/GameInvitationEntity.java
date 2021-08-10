package com.gmail.filimon24.adelin.chessratingsystem.persistence.entity;

import com.gmail.filimon24.adelin.chessratingsystem.Constants;
import com.gmail.filimon24.adelin.chessratingsystem.persistence.util.GameRequestState;
import lombok.Builder;
import lombok.Data;
import org.joda.time.DateTime;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;

@Entity
@Data
@Table(name = "game_invitation")
public class GameInvitationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(nullable = false)
    @ManyToOne
    private UserEntity sender;

    @JoinColumn(nullable = false)
    @ManyToOne
    private UserEntity receiver;

    @DateTimeFormat(pattern = Constants.DATETIME_FORMAT)
    @Column
    private DateTime date;

    @Column
    private GameRequestState requestState;

}
