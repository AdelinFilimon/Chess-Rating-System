package com.gmail.filimon24.adelin.chessratingsystem.persistence.util;

import lombok.Getter;

@Getter
public enum GameRequestState {
    PENDING("PENDING"),
    ACCEPTED("ACCEPTED"),
    REFUSED("REFUSED");

    private final String code;

    GameRequestState(String code) {
        this.code = code;
    }
}
