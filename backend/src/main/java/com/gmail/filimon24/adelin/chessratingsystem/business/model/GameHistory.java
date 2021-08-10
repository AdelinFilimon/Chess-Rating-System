package com.gmail.filimon24.adelin.chessratingsystem.business.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GameHistory {
    private Long id;
    private User whitePiecesPlayer;
    private User blackPiecesPlayer;
    private String gameOutcome;
    private String startingDate;
}
