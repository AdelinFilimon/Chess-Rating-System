package com.gmail.filimon24.adelin.chessratingsystem.business.service.util;

import com.gmail.filimon24.adelin.chessratingsystem.business.ranking.GamePredictionResult;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserGameStatisticsManager {
    private final List<Observer> observers = new ArrayList<>();
    private GamePredictionResult state;

    public void setState(GamePredictionResult state) {
        this.state = state;
        notifyAllObservers();
    }

    public void attach(Observer observer) {
        observers.add(observer);
    }

    public void notifyAllObservers() {
        for (Observer observer : observers) {
            observer.update(state);
        }
    }















}
