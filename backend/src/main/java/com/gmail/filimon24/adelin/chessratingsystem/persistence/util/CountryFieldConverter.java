package com.gmail.filimon24.adelin.chessratingsystem.persistence.util;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Arrays;
import java.util.stream.Collectors;

@Converter(autoApply = true)
public class CountryFieldConverter implements AttributeConverter<CountryField, String> {

    @Override
    public String convertToDatabaseColumn(CountryField countryField) {
        if (countryField == null) return null;
        return countryField.getCode();
    }

    @Override
    public CountryField convertToEntityAttribute(String countryCode) {
        if (countryCode == null) return null;
        return Arrays.stream(CountryField.values())
                .filter(countryField -> countryField.getCode().equals(countryCode))
                .collect(Collectors.toList())
                .get(0);
    }
}
