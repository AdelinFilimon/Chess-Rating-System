package com.gmail.filimon24.adelin.chessratingsystem.persistence.entity;

import lombok.Getter;

@Getter
public enum Country {
    ROMANIA("RO"),
    ITALY("IT"),
    SPAIN("ES"),
    BELGIUM("BE"),
    FRANCE("FR"),
    AUSTRIA("AT"),
    UNITED_KINGDOM("UK"),
    UNITED_STATES_OF_AMERICA("USA"),
    GERMANY("DE");

    private final String acronym;

    Country(String acronym) {
        this.acronym = acronym;
    }

}
