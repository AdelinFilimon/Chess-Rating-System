package com.gmail.filimon24.adelin.chessratingsystem.persistence.util;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Arrays;
import java.util.stream.Collectors;

@Converter(autoApply = true)
public class GameResultStateConverter implements AttributeConverter<GameResultState, String> {
    @Override
    public String convertToDatabaseColumn(GameResultState gameResultState) {
        return gameResultState.name();
    }

    @Override
    public GameResultState convertToEntityAttribute(String s) {
        return Arrays.stream(GameResultState.values())
                .filter((r) -> r.name().equals(s))
                .collect(Collectors.toList())
                .get(0);
    }
}
