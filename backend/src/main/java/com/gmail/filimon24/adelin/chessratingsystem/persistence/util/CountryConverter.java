package com.gmail.filimon24.adelin.chessratingsystem.persistence.util;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Arrays;
import java.util.stream.Collectors;

@Converter(autoApply = true)
public class CountryConverter implements AttributeConverter<Country, String> {

    @Override
    public String convertToDatabaseColumn(Country country) {
        if (country == null) return null;
        return country.getCode();
    }

    @Override
    public Country convertToEntityAttribute(String countryCode) {
        if (countryCode == null) return null;
        return Arrays.stream(Country.values())
                .filter(country -> country.getCode().equals(countryCode))
                .collect(Collectors.toList())
                .get(0);
    }
}
