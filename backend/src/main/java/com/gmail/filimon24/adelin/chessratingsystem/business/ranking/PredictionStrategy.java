package com.gmail.filimon24.adelin.chessratingsystem.business.ranking;

import com.gmail.filimon24.adelin.chessratingsystem.business.model.User;

public interface PredictionStrategy {
    GamePredictionResult predictGame(User player1, User player2);
}
