package com.gmail.filimon24.adelin.chessratingsystem.business.ranking;

import com.gmail.filimon24.adelin.chessratingsystem.business.model.User;
import com.gmail.filimon24.adelin.chessratingsystem.persistence.util.GameResultState;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GamePredictionResult {
    private GameResultState gameResultState;
    private Integer whitePiecesPlayerNewRating;
    private Integer blackPiecesPlayerNewRating;
    private User whitePiecesPlayer;
    private User blackPiecesPlayer;
}
