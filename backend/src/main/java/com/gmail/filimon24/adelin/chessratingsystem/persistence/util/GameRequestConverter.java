package com.gmail.filimon24.adelin.chessratingsystem.persistence.util;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Arrays;
import java.util.stream.Collectors;

@Converter(autoApply = true)
public class GameRequestConverter implements AttributeConverter<GameRequestState, String> {
    @Override
    public String convertToDatabaseColumn(GameRequestState gameRequestState) {
        return gameRequestState.getCode();
    }

    @Override
    public GameRequestState convertToEntityAttribute(String s) {
        return Arrays.stream(GameRequestState.values())
                .filter((r) -> r.getCode().equals(s))
                .collect(Collectors.toList())
                .get(0);
    }
}
