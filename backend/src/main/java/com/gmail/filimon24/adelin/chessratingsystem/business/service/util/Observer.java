package com.gmail.filimon24.adelin.chessratingsystem.business.service.util;

import com.gmail.filimon24.adelin.chessratingsystem.business.ranking.GamePredictionResult;

abstract class Observer {
    protected UserGameStatisticsManager userGameStatisticsManager;
    public abstract  void update(GamePredictionResult state);
}
